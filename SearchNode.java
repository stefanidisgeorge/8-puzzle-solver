package pkg8puzzle;

public class SearchNode
{

	private EightPuzzleState curState;      //h twrinh katastash
	private SearchNode parent;              //o kombos tou gonea
	private double cost;                    // kostos gia na ftasei se auth thn katastash
	private double hCost;                   // heuristic cost
	private double fCost;                   // f(n) cost = g(n) + h(n)

	
	public SearchNode(EightPuzzleState s)
	{
		curState = s;
		parent = null;
		cost = 0;
		hCost = 0;
		fCost = 0;
	}

	
	 // prev    gonios  (previous)           
	 // s       h katastash (state)            
	 // c       to kostos (g(n)) gia na ftasei ston kombo
	 // h       to kostos tou heuristic gia na ftasei ston kombo	             
	public SearchNode(SearchNode prev, EightPuzzleState s, double c, double h)
	{
		parent = prev;
		curState = s;
		cost = c;
		hCost = h;
		fCost = cost + hCost;
	}

	//epistrefei thn parousa katastsash
	public EightPuzzleState getCurState()
	{
		return curState;
	}

	//epistrefei ton patera
	public SearchNode getParent()
	{
		return parent;
	}

	//epistrefei to kostos
	public double getCost()
	{
		return cost;
	}

	//epistrefei to kostos ths euristikhs
	public double getHCost()
	{
		return hCost;
	}

	public double getFCost()
	{
		return fCost;
	}
}