import java.util.ArrayList;
import java.util.List;
public class AIPlayer
{
	int ROWS=6;//total rows
	int COLS=6;//total coloumns
	Cell[][] cells;//array of cells 
	Color myColor;
	Color oppColor;
	public AIPlayer(Board board)
	{
		cells=board.cells;
	}
	public void setColor(Color color)
	{
		this.myColor=color;
		oppColor=(myColor==Color.RED)?Color.YELLOW:Color.RED;
	}
	
	public int[] move()//to determine the move
	{
		int[] result= minimax(myColor,3,Integer.MIN_VALUE,Integer.MAX_VALUE);
		return new int[] {result[1],result[2]};
	}
	int evaluate()//for evaluating score
{
	int score=0;
	score+=evaluateRows();//evaluate score of rows
	score+=evaluateCol();//evaluate score of coloumns
	score+=evaluateDiag();//evaluate score diagonally
	score+=evaluateAntiDiag();//evaluate score antidiagonally
	return score;
}
int evaluateRows()//3 combinations in one row; total 18 combinations
{
	int score=0;
	for(int i=0;i<6;i++)
	{
		for(int j=0;j<3;j++)
		score+=evaluateLine(i,j,i,j+1,i,j+2,i,j+3);
	}
	return score;
}

public int evaluateCol()//3 combinations in one coloumn; total 18 combinations
{
	int score=0;
		for(int i=0;i<6;i++)
	{
		for(int j=0;j<3;j++)
		score+=evaluateLine(j,i,j+1,i,j+2,i,j+3,i);
	}
	return score;
}

public int evaluateDiag()//9 combinations diagonally
{
	int score=0;
	for(int i=0,k=1;i>=0;i--)
	{
		int l=i;
		for(int j=0;j<=k;j++,l++)
		{
			score+=evaluateLine(l,j,l+1,j+1,l+2,j+2,l+3,j+3);
		}
	}
	for(int i=1;i<3;i++)
	{
		int l=i;
		for(int j=0;j<=2-i;j++,l++)
		{
			score+=evaluateLine(j,l,j+1,l+1,j+2,l+2,j+3,l+3);
		}
	}
	return score;
}

public int evaluateAntiDiag()//9 combinations anti diagonally
{
	int score=0,l;
	for(int i=3;i<6;i++)
	{
		l=i;
		for(int j=0;j<i-2;j++)
		{
			score+=evaluateLine(j,l,j+1,l-1,j+2,l-2,j+3,l-3);
		}
	}
	for(int i=1,k=3;i<3;i++,k++)
	{
		l=i;
		for(int j=5;j>k;j--,l++)
		{
			score+=evaluateLine(l,j,l+1,j-1,l+2,j-2,l+3,j-3);
		}
	}
	return score;
}

public int evaluateMax(int score)// evaluate score for maximizing player
{
	if(score>0)
	score*=10;
	else if(score<0)
	return 0;
	else score=1;
	return score;
}

public int evaluateMin(int score) // evaluate score for minimizing player
{
	if(score<0)
	score*=10;
	else if(score>0)
	return 0;
	else score=-1;
	return score;
}

private int evaluateLine(int row1,int col1,int row2,int col2,int row3, int col3, int row4, int col4)//evaluate score of a 4-in-a-line
{
	//first cell
	int score=0;
	if(cells[row1][col1].content==myColor)
	score=1;
	else if(cells[row1][col1].content==oppColor)
	score=-1;
	
	//second cell
	if(cells[row2][col2].content==myColor)
	score = evaluateMax(score);
	else if(cells[row2][col2].content==oppColor)
	score=evaluateMin(score);
	if(score==0)
	return 0;
	
	//third cell
	if(cells[row3][col3].content==myColor)
	score=evaluateMax(score);
	else if(cells[row3][col3].content==oppColor)
	score=evaluateMin(score);
	if(score==0)
	return 0;
	
	//fourth cell
	if(cells[row4][col4].content==myColor)
	score=evaluateMax(score);
	else if(cells[row4][col4].content==oppColor)
	score=evaluateMin(score);
	
	return score;
}

private List<int[]> generateMoves()//generate all possible moves 
{
	List<int[]> possibleMoves=new ArrayList<int[]>();//possible moves stores all moves
	if(gameWon(myColor)||gameWon(oppColor))
	return possibleMoves;
	for(int row=0;row<6;row++)
	{
		for(int col=0;col<6;col++)
		{
			if(cells[row][col].content==Color.EMPTY)//if the cell is empty
			possibleMoves.add(new int[] {row,col});//this is a possible move, adding it to the list
		}
	}
	return possibleMoves;
}

private long[] winningPatterns={
0b000000000000000000000000000000001111L,
0b000000000000000000000000000000011110L,
0b000000000000000000000000000000111100L,
0b000000000000000000000000001111000000L,
0b000000000000000000000000011110000000L,
0b000000000000000000000000111100000000L,
0b000000000000000000001111000000000000L,
0b000000000000000000011110000000000000L,
0b000000000000000000111100000000000000L,
0b000000000000001111000000000000000000L,
0b000000000000011110000000000000000000L,
0b000000000000111100000000000000000000L,
0b000000001111000000000000000000000000L,
0b000000011110000000000000000000000000L,
0b000000111100000000000000000000000000L,
0b001111000000000000000000000000000000L,
0b011110000000000000000000000000000000L,
0b111100000000000000000000000000000000L,

//coloumns
0b000000000000000001000001000001000001L,
0b000000000001000001000001000001000000L,
0b000001000010000001000001000000000000L,
0b000000000000000010000010000010000010L,
0b000000000010000010000010000010000000L,
0b000010000010000010000010000000000000L,
0b000000000000000100000100000100000100L,
0b000000000100000100000100000100000000L,
0b000100000100000100000100000000000000L,
0b000000000000001000001000001000001000L,
0b000000001000001000001000001000000000L,
0b001000001000001000001000000000000000L,
0b000000000000010000010000010000010000L,
0b000000010000010000010000010000000000L,
0b010000010000010000010000000000000000L,
0b000000000000100000100000100000010000L,
0b000000100000100000100000100000000000L,
0b100000100000100000100000000000000000L,

//anti-diagonally
0b000000000000000001000010000100001000L,
0b000000000000000010000100001000010000L,
0b000000000001000010000100001000000000L,
0b000000000000000100001000010000100000L,
0b000000000010000100001000010000000000L,
0b000001000010000100001000000000000000L,
0b000000000100001000010000100000000000L,
0b000010000100001000010000000000000000L,
0b000100001000010000100000000000000000L,

//diagonally
0b00000000000001000000100000010000001L,
0b00000010000001000000100000010000000L,
0b10000010000001000000100000000000000L,
0b00000000000010000001000000100000010L,
0b00000100000010000001000000100000000L,
0b00000000000100000010000001000000100L,
0b00000000100000010000001000000100000L,
0b01000000100000010000001000000000000L,
0b00100000010000001000000100000000000L

};
private boolean gameWon(Color player)
{
	long pattern = 0b00000000000000000000000000000000000L;
	for(int row=0;row<ROWS;row++)
	{
		for(int col=0;col<COLS;col++)
		{
			if(cells[row][col].content==player)
			{
				pattern |= (1<<(row*COLS+col));
			}
		}
	}
	for(long winningPattern:winningPatterns)
	{
		if((pattern&winningPattern)==winningPattern) return true;
	}
	return false;
}
private int[] minimax(Color player, int depth, int alpha, int beta)//minimax algorithm with alpha beta pruning
{
	int score;
	int bestRow=-1;//initializing with a garbage value
	int bestCol=-1;//initializing with a garbage value
	List<int[]> possibleMoves=generateMoves();//generate moves
	if(possibleMoves.isEmpty()||depth==0)//if no possible move is found
	{
		score=evaluate();//find the score
		return new int[] {score,bestRow,bestCol};
	}
	else//if moves are found
	{
		for(int[] move:possibleMoves)
		{
			cells[move[0]][move[1]].content=player;//fill a cell
			if(player==myColor)//if maximizing player's turn
			{
				score=minimax(oppColor,depth-1,alpha,beta)[0];
				if(score>alpha)
				{
					alpha=score;
					bestRow=move[0];
					bestCol=move[1];
				}
			}
			else //if minimizing player's turn
			{
				score=minimax(myColor,depth-1,alpha,beta)[0];
				if(score<beta)
				{
					beta=score;
					bestRow=move[0];
					bestCol=move[1];
				}
			}
			cells[move[0]][move[1]].clear();
			if(alpha>=beta)//if alpha>beta, no more checking needs to be done
			break;
		}
		return new int[] {(player == myColor) ? alpha : beta, bestRow, bestCol};//return alpha for maximizing player and beta for minimizing
		
	}
}
}
