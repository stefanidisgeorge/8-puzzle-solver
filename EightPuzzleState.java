package pkg8puzzle;
import java.util.ArrayList;
import java.util.Arrays;

/*
* H klash EightPuzzle orizei thn arxikh katastash tou problhmatos. 
* To board anaparistate ws enas monodiastatos pinakas, 
* to 0 einai to keno kai to xeirizomaste eidika otan dhmiourgountai paidia kombwn
*/

public class EightPuzzleState implements State
{

	private final int PUZZLE_SIZE = 9;  //mege8os tou board
	private int outOfPlace = 0;         //to plh8os twn tetragwnwn pou briskontai ektos 8eshs se sxesh me to stoxo
        public static String path = "";

	private int manDist = 0;            //apostash Manhattan

	private final int[] GOAL = new int[]{ 1, 2, 3, 8,  0, 4, 7, 6, 5 };    //katastash stoxou GOAL
	
	private int[] curBoard;

	public EightPuzzleState(int[] board)
	{
		curBoard = board;
		setOutOfPlace();
		
	}

      
	/*kostos gia na ftasei sthn katastash*/
        @Override
	public double findCost()
	{
		return 1;
	}

        
        
        /* h parakatw methodos upologizei to kostos tou Manhattan heuristic gia to board */
	private void setManDist()
	{
		int index = -1;

		// diathrei thn 8esh panw sto board
		// den mporei na xrhsimopoihu8ei to 0 opote oi times autes prepei na mpoun sth swsth 8esh
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				index++;

				// afairei -1 apo to val wste na brei to index opou 8a eprepe na vriskete h timh				
				int val = (curBoard[index] - 1);

                                //ean den vriskomaste sthn kenh 8esh (0), tote auth 8a vrisketai sthn 8esh -1 epeidh afairesame -1 
                                //nwritera gia na allaksoume to val se index
				if (val != -1)
				{
					// orizontio mege8os, mod thn timh tou keliou me thn orizontia diastash					
					int horiz = val % 3;
					// katakorufo mege8os, diairesh ths timhs me thn katakorufh diastash					
					int vert = val / 3;

					manDist += Math.abs(vert - (y)) + Math.abs(horiz - (x));    //upologismos ths apostashs Manhattan 
				}
				// ean vrisketai sto keno (0), tote to prosperna
			}
		}
	}
        
        
        
	/*h methodos auth vriskei posa tetragwna tou board einai ektos 8eshs se sxesh me ton stoxo*/
	private void setOutOfPlace()
	{
		for (int i = 0; i < curBoard.length; i++)
		{
			if (curBoard[i] != GOAL[i])
			{
				outOfPlace++;
			}
		}
	}



	/*
	   entopizei to (0) sto board kai epistrefei th 8esh tou
	 */
	private int getHole()
	{
		//ean epistrafei -1 tote proekupse la8os giati prepei na uparxei 0 sto board 
		int holeIndex = -1;

		for (int i = 0; i < PUZZLE_SIZE; i++)
		{
			if (curBoard[i] == 0)
				holeIndex = i;
		}
		return holeIndex;
	}

	public int getOutOfPlace()
	{
		return outOfPlace;
	}

        //epistrefei thn apostash Manhattan
	public int getManDist()
	{
		return manDist;
	}

	/* kanei antigrafo tou pinaka pou dinetai ws orisma*/
	private int[] copyBoard(int[] state)
	{
		int[] ret = new int[PUZZLE_SIZE];
		for (int i = 0; i < PUZZLE_SIZE; i++)
		{
			ret[i] = state[i];
		}
		return ret;
        }
        
        /* To keno (0) mporei na kinh8ei me megisto 4 8eseis.
         * olis8ainei se 4 kateu8unseis ean brisketai sth mesh tou board
         * 2 kateu8unseis ean brisketai se gwnia 
         * kai 3 kateu8unseis ean brisketai sth mesh mias seiras (ektos apo th mesaia)
        
        Epistrefei ena ArrayList me ola ta paidia gia thn katastash
        */
        
       	@Override
	public ArrayList<State> genSuccessors()
	{
		ArrayList<State> successors = new ArrayList<State>();
		int hole = getHole();

		// dhmiourgei state me olis8hsh aristera tou kenou
		// ena epitrepetai
		if (hole != 0 && hole != 3 && hole != 6)
		{
			/*
			 * mporei na olis8hsei aristera, sto keno opote dhmiourgei neo state
			 * kai to apo8hkeuei sta paidia
			 */
                        //System.out.println("left");
                        //path = path + "LEFT";
			swapAndStore(hole - 1, hole, successors);
		}

		// dhmiourgei katastash me olis8hsh pros ta panw 
		if (hole != 6 && hole != 7 && hole != 8)
		{
                        //System.out.println("up");
                        //path = path + "UP";
			swapAndStore(hole + 3, hole, successors);
		}

		// dhmiourgei katastash me olis8hsh pros ta katw
		if (hole != 0 && hole != 1 && hole != 2)
		{
                        //System.out.println("down");
                        //path = path + "DOWN";
			swapAndStore(hole - 3, hole, successors);
		}
		// dhmiourgei katastash me olis8hsh pros ta deksia
		if (hole != 2 && hole != 5 && hole != 8)
		{
                        //System.out.println("right");
                        //path = path + "RIGHT";
			swapAndStore(hole + 1, hole, successors);
		}

		return successors;  //epistrefei ta paidia
	}

        /* allazei ta dedomena twn 8esewn d1 kai d2, se ena antigrafo tou twrinou board
           dhmiourgei mia nea katastash sumfwna me to neo board kai to kanei push sto ArrayList s 
        */   
	private void swapAndStore(int d1, int d2, ArrayList<State> s)
	{
		int[] cpy = copyBoard(curBoard);
		int temp = cpy[d1];
		cpy[d1] = curBoard[d2];
		cpy[d2] = temp;
		s.add((new EightPuzzleState(cpy)));
	}

        // elegxei ena h twrinh katastash einai h katastash stoxou kai epistrefei true, an nai
	public boolean isGoal()
	{
		if (Arrays.equals(curBoard, GOAL))
		{
			return true;
		}
		return false;
	}

        public String getPath()
        {
            return path;
        }
        
        //ektupwnei thn katastash tou board
	public void printState()
	{
		System.out.println(curBoard[0] + " | " + curBoard[1] + " | "
				+ curBoard[2]);
		System.out.println("---------");
		System.out.println(curBoard[3] + " | " + curBoard[4] + " | "
				+ curBoard[5]);
		System.out.println("---------");
		System.out.println(curBoard[6] + " | " + curBoard[7] + " | "
				+ curBoard[8]);

	}

        //uperfortwsh ths equal wste na sugkrinei duo katastaseis kai epistrefei true, an nai
	public boolean equals(State s)
	{
		if (Arrays.equals(curBoard, ((EightPuzzleState) s).getCurBoard()))
		{
			return true;
		}
		else
			return false;

	}

	public int[] getCurBoard()
	{
		return curBoard;
	}

}