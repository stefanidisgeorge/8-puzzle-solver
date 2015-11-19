package pkg8puzzle;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import pkg8puzzle.EightPuzzleState;
import pkg8puzzle.SearchNode;


public class AStarSearch
{
        public static int a;
	
	public static int search(int[] board)
	{
                int my_timeout = 30000; //30 deuterolepta orio ektelehshs
                int number_of_nodes;
                
		SearchNode root = new SearchNode(new EightPuzzleState(board));
		Queue<SearchNode> q = new LinkedList<SearchNode>();
		q.add(root);

		int searchCount = 1; // metrhths gia to plhthos twn epanalhpsewn
                long lStartTime = new Date().getTime();      
   
		while (!q.isEmpty()) // oso to queue den einai adeio
		{
                    SearchNode tempNode = (SearchNode) q.poll();    //anagnwsh kai afairesh tou prwtou stoixeiou ths ouras
                    
                    long lEndTime = new Date().getTime(); // end time
                    long difference = lEndTime - lStartTime; // check different
                    if (difference > my_timeout){
                        System.out.println("Haven't found the solution, in " + my_timeout/1000 + " secs, I stop it "
                                                        + "because I don't wanna burn my laptop");
                        System.out.println("The cost was: " + tempNode.getCost());
                        System.out.println("The number of nodes examined: "
							+ searchCount);
                        
                        return searchCount;
                    }
                
			

			// ean o TemNode den einai o stoxos
			if (!tempNode.getCurState().isGoal())
			{
				// paragei to paidi tou tempNode
				ArrayList<State> tempSuccessors = tempNode.getCurState()
						.genSuccessors();
				ArrayList<SearchNode> nodeSuccessors = new ArrayList<SearchNode>();

				/*
				 * broxos gia ta paidia tou tempNode, metatroph tous se SearchNode
				 * elegxos ean exoun episkef8ei nwritera, an oxi tote prosti8entai sthn oura				
				 */
				for (int i = 0; i < tempSuccessors.size(); i++)
				{
					SearchNode checkedNode;
					// dhmiourgia kombou
					
						/*
						 * dhmiourgia SearchNode, me tempNode opws o gonios
						 * to kostos tou tempNode cost+ new cost (1) ,gia auth thn katastash,
						 * kai thn timh tou heuristic                         
						 */
											
						checkedNode = new SearchNode(tempNode, (EightPuzzleState) tempSuccessors.get(i), tempNode.getCost()
										+ tempSuccessors.get(i).findCost(),
								((EightPuzzleState) tempSuccessors.get(i))
										.getManDist());
					

					// elegxos ean exei episkeuf8ei nwritera
					if (!checkRepeats(checkedNode))
					{
						nodeSuccessors.add(checkedNode);    //an oxi, eisagete
					}
				}

				// elegxos ean to paidi einai adeio, an nai sunexizei
				// to broxo apo thn korufh 
				if (nodeSuccessors.size() == 0)
					continue;

				SearchNode lowestNode = nodeSuccessors.get(0);

				/*
				 * o borxos auos vriskei to xamhlotero f(n) se ena kombo 
				 * kai ton orizei ws ton xamhlotero
				 */
				for (int i = 0; i < nodeSuccessors.size(); i++)
				{
					if (lowestNode.getFCost() > nodeSuccessors.get(i)
							.getFCost())
					{
						lowestNode = nodeSuccessors.get(i);
					}
				}

				int lowestValue = (int) lowestNode.getFCost();

				// eisagwgh ka8e kombou thn idia katwterh timh
				for (int i = 0; i < nodeSuccessors.size(); i++)
				{
					if (nodeSuccessors.get(i).getFCost() == lowestValue)
					{
						q.add(nodeSuccessors.get(i));
					}
				}

				searchCount++;
			}
			else
			// vre8hke h katastash stoxou, ektupwsh tou monopatiou pou akolou8h8eike 
			{
				// xrhsh stack gia thn apo8hkeush tou monopatiou apo thn arxikh katastash
				// mexri ton stoxo
				Stack<SearchNode> solutionPath = new Stack<SearchNode>();
				solutionPath.push(tempNode);
				tempNode = tempNode.getParent();

				while (tempNode.getParent() != null)
				{
					solutionPath.push(tempNode);
					tempNode = tempNode.getParent();
				}
				solutionPath.push(tempNode);

				// to mege8os tou stack prin thn prospelash kai to adeiasma tou
				int loopSize = solutionPath.size();

				for (int i = 0; i < loopSize; i++)
				{
					tempNode = solutionPath.pop();
					tempNode.getCurState().printState();
					System.out.println();
					System.out.println();
				}
				System.out.println("The cost was: " + tempNode.getCost());

					System.out.println("The number of nodes examined: "
							+ searchCount);
                                        a++;
				                                                           
				System.out.println("END\n");
                                return searchCount;
			}
		}	
                return searchCount;
	}

	/*
	 * Methodos gia thn a3iologhsh tou SearchNode, ean exei episkef8ei nwritera
	 * Epistrefei true ean exei episkef8ei alliws false 
	 */
	private static boolean checkRepeats(SearchNode n)
	{
		boolean retValue = false;
		SearchNode checkNode = n;

		// Oso o gonios tou n den einai null, elegxos ean einai idios me to node stoxo
		while (n.getParent() != null && !retValue)
		{
			if (n.getParent().getCurState().equals(checkNode.getCurState()))
			{
				retValue = true;
			}
			n = n.getParent();
		}

		return retValue;
	}

}