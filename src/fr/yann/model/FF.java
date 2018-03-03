package fr.yann.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//FordFulkerson
public class FF
{
    private int[] parent;
    private Queue<Integer> queue;
    private int nbNodes;
    private boolean[] visited;

    public FF(int nbNodes)
    {
        this.nbNodes = nbNodes;
        this.queue = new LinkedList<Integer>();
        parent = new int[nbNodes];
        visited = new boolean[nbNodes];
    }

    //retourne true si on peut trouver un chemin entre la source et la dest dans le graph en param
    private boolean bfs(int source, int goal, int graph[][])
    {
        boolean pathFound = false;
        int destination, element;

        for(int vertex = 0; vertex < nbNodes; vertex++) //initialisation des tableaux
        {
            parent[vertex] = -1;
            visited[vertex] = false;
        }

        queue.add(source);
        parent[source] = -1;
        visited[source] = true;

        while (!queue.isEmpty()) //tant qu'on est pas bloqué dans notre chemin
        {
            element = queue.remove();
            destination = 0;

            while (destination <= nbNodes-1) //on visite un ligne de la matrice (ligne [element])
            {
                //si destination est un successeur du noeud element et qu'on ne l'a pas encore visit�
                if (graph[element][destination] > 0 &&  !visited[destination])
                {
                    parent[destination] = element; //le parent de destination prend comme valeur element
                    queue.add(destination);
                    visited[destination] = true;
                }
                destination++;
            }
        }
        if(visited[goal])
        {
            pathFound = true;
        }
        return pathFound;
    }

    public int fordFulkerson(ArrayList<ArrayList<Integer>> graph, int source, int destination) //retourne le maxFlow du graph
    {
        int u, v;
        int maxFlow = 0;
        int pathFlow;

        int[][] residualGraph = new int[nbNodes][nbNodes];
        for (int i = 0; i < nbNodes; i++) //copie du graph d'origine dans le r�sidual graph
        {
            for (int j = 0; j < nbNodes; j++)
            {
                residualGraph[i][j] = graph.get(i).get(j);
            }
        }

        while (bfs(source ,destination, residualGraph)) //tant qu'il y a des chemins entre la source et la destination (la deuxi�me it�ration peut donner des chemins "backwards")
        {
            pathFlow = Integer.MAX_VALUE;
            for (v = destination; v != source; v = parent[v]) //on r�cup�re la capacit� minimale du chemin
            {
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            for (v = destination; v != source; v = parent[v]) //ajout du flow minimum a tous les edges du chemin (r�sidual graph)
            {
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
            maxFlow += pathFlow;
        }

        return maxFlow;
    }

	public int[] getParent()
	{
		return this.parent;
	}

	public ArrayList<int[]> getMinimumCut(ArrayList<ArrayList<Integer>> graph)
	{
		ArrayList<int []> minimumCut = new ArrayList<>();

		for (int i=0; i<visited.length; i++)
		{
			if (visited[i] == true)
			{
				for (int j=0; j<visited.length; j++)
				{
					if ((visited[j]==false) && (graph.get(i).get(j)>0))  //If false and if there is an edge between the two vertices
					{
						int[] tempoTab = new int[2];
			        	tempoTab[0]= i;
			        	tempoTab[1]= j;
			        	minimumCut.add(tempoTab);
					}
				}
			}
		}

		return minimumCut;
	}
}
