import java.util.Scanner;

class GameMain
{
	Board board;
	AIPlayer comp;
	Color player,currentPlayer;
	GameState currentState;
	Scanner sc= new Scanner(System.in);
	int[] move;
	public GameMain()
	{
		currentState=GameState.PLAYING;
		board=new Board();
		move = new int[2];
		comp=new AIPlayer(board);
		comp.setColor(Color.RED);
		player=Color.YELLOW;
		currentPlayer=Color.YELLOW;
		do
		{
			board.paint();
			System.out.println(currentPlayer);
			playerMove(currentPlayer);
			updateGame(currentPlayer);
			currentPlayer=(currentPlayer==player)?comp.myColor:player;
		}while(currentState==GameState.PLAYING);
		board.paint();
		if(currentState==GameState.RED_WON)
		{
			System.out.println("Red wins");
		}
		else if(currentState==GameState.YELLOW_WON)
		{
			System.out.println("YELLOW wins");
		}
		else System.out.println("its a draw");
	}
	public void updateGame(Color player)
	{
		if(board.gameWon(player))
		{
			currentState=(player==Color.RED)?GameState.RED_WON:GameState.YELLOW_WON;
		}
		else if(board.isDraw())
		{
			currentState=GameState.DRAW;
		}
	}
	public void playerMove(Color playr)
	{
		if(playr==player)
		{
			do
			{
				System.out.print("Enter row and coloumn(0-5)");
				move[0]=sc.nextInt();
				move[1]=sc.nextInt();
			}while(board.cells[move[0]][move[1]].content!=Color.EMPTY);
		}
		else
		{
			move=comp.move();
		}
		board.cells[move[0]][move[1]].content=playr;
	}
	public static void main(String[] args)
	{
		new GameMain();
	}
	
}
