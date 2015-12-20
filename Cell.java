class Cell
{
	int row;
	int col;
	Color content;
	public void clear()
	{
		content=Color.EMPTY;
	}
	public Cell(int x, int y)
	{
		row=x;
		col=y;
		clear();
	}
	public void paint()
	{
		switch(content)
		{
			case RED : System.out.print("R"); break;
			case YELLOW : System.out.print("Y"); break;
			case EMPTY : System.out.print(" "); break;
		}
	}
}
