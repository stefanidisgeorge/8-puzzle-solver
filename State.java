package pkg8puzzle;

import java.util.ArrayList;


public interface State
{
	// proka8orizei thn me8odo pou elegxei ean bre8hke h katastash stoxou
	boolean isGoal();

	// ArrayList tupou State gia thn dhmiourgia apogononwn State
	ArrayList<State> genSuccessors();

	// proka8orizei thn methodo pou upologizei to kostos apo thn arxikh katastash ws thn parousa
	double findCost();

	// ektupwsh ths parousas katastashs
	public void printState();

	// compare the actual state data    
	public boolean equals(State s);
}
