package pkg8puzzle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Date;

public class BFSearch
{
         public static int a;

	public static int search(int[] board)
	{
                int number_of_nodes;
		SearchNode root = new SearchNode(new EightPuzzleState(board));
		Queue<SearchNode> queue = new LinkedList<SearchNode>();

		queue.add(root);

		number_of_nodes = performSearch(queue);
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

	public static int performSearch(Queue<SearchNode> q/*, boolean d*/)
	{
		int searchCount = 1;    // metrhths gia to plhthos twn epanalhpsewn
                int my_timeout = 30000; //30 deuterolepta orio ektelehshs
                
                long lStartTime = new Date().getTime();

		while (!q.isEmpty()) // oso to queue den einai adeio
		{
                       
			SearchNode tempNode = (SearchNode) q.poll();    //anagnwsh kai afairesh tou prwtou stoixeiou ths ouras
                                                                        //to tempNode einai o trexwn kombos
                        long lEndTime = new Date().getTime(); // end time
                        long difference = lEndTime - lStartTime; // check different
                        
                        //xronometro gia diakoph ths ekteleshs meta apo my_timeout sec
                        if (difference > my_timeout){     
                            System.out.println("Haven't found the solution, in " + my_timeout/1000 + " secs, I stop it "
                                                        + "because I don't wanna burn my laptop");
                            System.out.println("The cost was: " + tempNode.getCost());
                            System.out.println("The number of nodes examined: "
							+ searchCount);
                            System.out.println("It needed: "
							+ lEndTime + " seconds ");
                            return searchCount;
                        }

			if (!tempNode.getCurState().isGoal()) // ean o tempNode den einai o stoxos													
			{
				ArrayList<State> tempSuccessors = tempNode.getCurState()
						.genSuccessors(); // paragei to paidi tou tempNode
											

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
                                        
                                        //q.add(newNode);               //ek twn proterwn pros8hkh tou neou kombou
					if (!checkRepeats(newNode))     //elegxos ean exei episkef8ei nwritera
					{
						q.add(newNode);         //pros8hkh neou kombou 
                                                //q.remove(newNode);    //afairesh tou kombou pou proste8hke sto SearchNode
					}
				}
				searchCount++;                          //metrhths twn kombwn pou exoun elegxei
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

                                        System.out.println("I clear");
                                        q.clear();
                                        a++;
                                        
                               System.out.println("I clear");
                                q.clear();
                                return searchCount;
			}
		}
                return searchCount;    
	}
        
     
}