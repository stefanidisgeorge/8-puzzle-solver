package pkg8puzzle;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;


public class DFSearch
{
        public static int a;
	
	public static int search(int[] board)
	{
                int number_of_nodes;
		SearchNode root = new SearchNode(new EightPuzzleState(board));
		Stack<SearchNode> stack = new Stack<SearchNode>();

		stack.add(root);

                number_of_nodes = performSearch(stack);
                return number_of_nodes;
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

	public static int performSearch(Stack<SearchNode> s)
	{
		int searchCount = 1;    // metrhths gia to plhthos twn epanalhpsewn
                int my_timeout = 30000; //30 deuterolepta orio ektelehshs
                long lStartTime = new Date().getTime();
		
                while (!s.isEmpty()) // oso to queue den einai adeio
		{
                    
                    SearchNode tempNode = (SearchNode) s.pop();
                    
                     long lEndTime = new Date().getTime(); // end time
                        long difference = lEndTime - lStartTime; // check different
                        if (difference > my_timeout){
                            System.out.println("Haven't found the solution, in " + my_timeout/1000 + " secs, I stop it "
                                                        + "because I don't wanna burn my laptop");
                            System.out.println("The cost was: " + tempNode.getCost());
                            System.out.println("The number of nodes examined: "
							+ searchCount);

                            return searchCount ;
                        }
                        
			

			// ean o TemNode den einai o stoxos
			if (!tempNode.getCurState().isGoal())
			{
				// paragei to paidi tou tempNode
				ArrayList<State> tempSuccessors = tempNode.getCurState()
						.genSuccessors();

				/*
				 * broxos gia ta paidia tou tempNode, metatroph tous se SearchNode
				 * elegxos ean exoun episkef8ei nwritera, an oxi tote prosti8entai sthn oura				
				 */
				for (int i = 0; i < tempSuccessors.size(); i++)
				{
					// h deuterh parametros pros8etei to kostos tou neou kombou
					// sto twrino sunoliko kostos tou SearchNode 

                                    SearchNode newNode = new SearchNode(tempNode, (EightPuzzleState) tempSuccessors.get(i), tempNode.getCost()
									+ tempSuccessors.get(i).findCost(), 0);
                                    
					if (!checkRepeats(newNode))
					{
						s.add(newNode);
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
}