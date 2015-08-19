package gameOfCluedo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;

import gameOfCluedo.squares.*;

public class Board {
	private BufferedImage boardImage = null;
	private BufferedImage[] playerTokenImage = null;

	public final int xSize = 25;
	public final int ySize = 25;
	public static final int GRID_SIZE = 38;
	public static final int OFF_X = 40;
	public static final int OFF_Y = 24;
	public static final int SIZE = 1024;

	private Square[][] board = new Square[xSize][ySize];
	private List<Position> highlightPositions = new ArrayList<Position>();
	public final List<Room> rooms = new ArrayList<Room>();
	private final String[] roomTitles = {"Kitchen","Ball Room", "Conservatory", "Dining Room",
										"Billiard Room", "Library", "Lounge", "Hall", "Study"};
	private final int[][] roomCoordinates = {{1,4},{10,5},{19,3}, {2,13},{19,11},{19,17},{2,22}, {10,22}, {19,23}};
	private Map<Player, Position> playerPos = new HashMap<Player, Position>();
	//private final int[][] startingPositions = {{9,0}, {14,0}, {23,6}, {23,19}, {7, 24}, {0, 17}};
	private final int[][] startingPositions = {{7, 24}, {0, 17}, {9,0}, {14,0}, {23,6}, {23,19}};
	/*
	 * 	Miss_Scarlett, Colonel_Mustard, Mrs_White,The_Reverend_Green ,Mrs_Peacock ,Professor_Plum
	 */
	public Board(String file){
		// Load Image
		try {
			boardImage = ImageIO.read(new File("src/Images/board.jpg"));
			playerTokenImage = new BufferedImage[]{ImageIO.read(new File("src/Images/Tokens/token_Scarlett.png")),
													ImageIO.read(new File("src/Images/Tokens/token_Mustard.png")),
													ImageIO.read(new File("src/Images/Tokens/token_White.png")),
													ImageIO.read(new File("src/Images/Tokens/token_Green.png")),
													ImageIO.read(new File("src/Images/Tokens/token_Peacock.png")),
													ImageIO.read(new File("src/Images/Tokens/token_Plum.png"))};
		} catch (IOException e) {
		}
		createRooms();
		//Scan File to create board
		try{
			Scanner s = new Scanner(new File(file));
			int xPos = 0;
			int yPos = 0;
			while(s.hasNext()){
				//Checks if int(hallway or room)
				if(s.hasNextInt()){
					int i = s.nextInt();
					if(i==0){
						board[xPos][yPos] = new BlankSquare();
					}else if(i==1){
						board[xPos][yPos] = new RoomSquare(null);
					}else{
						System.out.println("Error loading file (invalid int - " + i + ", xPos = " + xPos + ", yPox = " + yPos + ")");
					}
					//Else square is a door
				}else{
					String room = s.next();
					//Checks string was 1 char
					if(room.length()!=1){
						s.close();
						throw new IOException("Invalid string");
					}
					//gets char
					char c = room.charAt(0);
					//if c is a-z
					if((int)c>96 && (int)c<123){
						board[xPos][yPos] = new RoomSquare(rooms.get(((int)c)-97));
					}
					else if((int)c>64&&(int)c<91){//creates door to room (63 is A, rooms should be in orde of ABC in list)
						board[xPos][yPos] = new DoorSquare(rooms.get(((int)c)-65));
					}
				}
				//Updates x and y
				xPos++;
				if(xPos>=25){
					xPos=0;
					yPos++;
				}
			}
			s.close();
		}catch(IOException e){
			System.out.println("Error loading board - " + e.getMessage());
		}
		setupEntrances();
	}
	/**
	 * Adds player to map at their starting location
	 * @param player
	 */
	public void addPlayer(Player player){
		Position pos = new Position(startingPositions[player.getCharacter().ordinal()][0], startingPositions[player.getCharacter().ordinal()][1]);
		playerPos.put(player, pos);
	}

	/**
	 * Removes player from board and map
	 * @param player
	 */
	public void removePlayer(Player player){
		playerPos.remove(player);
	}

	/**
	 * Returns player position on the board. (null if player not found)
	 * @param p
	 * @return
	 */
	public Position getPlayerPosition(Player p){
		if(playerPos.get(p)!=null){
			return playerPos.get(p);
		}else{
			return null;
		}
	}

	/**
	 * Sets a players position on the board
	 * @param player
	 * @param pos
	 */
	public void setPlayerPosition(Player player, Position pos){
		if(!playerPos.containsKey(player)){return;}
		playerPos.put(player, pos);
		if(!pos.isRoom()){
			System.out.println(player.getCharacter().name().replace('_', ' ') + " is now at " + (char)(pos.getX()+65) + "," + (pos.getY()+1));
		}else{
			System.out.println(player.getCharacter().name().replace('_', ' ') + " is now in " + pos.getRoom());
		}
	}

	/**
	 * Finds reachable rooms from a position
	 * @param diceRoll
	 * @param startingPos
	 * @return
	 */
	public List<Room> reachableRooms(int diceRoll, Position startingPos){
		List<Room> reachable = new ArrayList<Room>();
		for(Position p : getValidMoves(startingPos, diceRoll)){
			if(p.isRoom()){
				reachable.add(p.getRoom());
			}
		}
		return reachable;
	}

	/**
	 * Finds reachable rooms from an x,y coordinate
	 * @param diceRoll
	 * @param x
	 * @param y
	 * @return
	 */
	public List<Room> reachableRooms(int diceRoll, int x, int y){
		return null;
	}

	/**
	 * Checks if player can move to target from current position, given the roll of the dice
	 * @param p
	 * @param target
	 * @param diceRoll
	 * @return
	 */
	public boolean validMove(Player p, Position target, int diceRoll){
		Position orgPos = getPlayerPosition(p);
		return getValidMoves(orgPos, diceRoll).contains(target);
	}

	/**
	 * Checks if can make it from one position to another, given the roll of the dice
	 * @param pos
	 * @param target
	 * @param diceRoll
	 * @return
	 */
	public boolean validMove(Position pos, Position target, int diceRoll){
		return getValidMoves(pos, diceRoll).contains(target);
	}

	/**
	 * Returns set of valid moves from position with dice roll
	 * @param pos
	 * @param movesLeft
	 * @param visited
	 * @return
	 */
	public Set<Position> getValidMoves(Position pos,  int diceRoll){
		Set<Position> validMoves = new HashSet<Position>();
		Queue<PosInfo> nextPos = new ArrayDeque<PosInfo>();
		//Add passage to set if original pos is a room with a passage
		if(pos.isRoom()&&pos.getRoom().getPassage()!=null){
			validMoves.add(new Position(pos.getRoom().getPassage()));
		}
		//Add surrounding positions to queue
		for(Position p : getSurroundingPositions(pos)){
			nextPos.add(new PosInfo(p, diceRoll-1));
		}
		while(!nextPos.isEmpty()){
			PosInfo posInfo = nextPos.poll();
			if(!validMoves.contains(posInfo.pos)){
				if(posInfo.pos.isRoom()||!playerPos.containsValue(posInfo.pos)){
					validMoves.add(posInfo.pos);
				}
				if(posInfo.movesLeft>0&&!posInfo.pos.isRoom()){
					for(Position neighbour : getSurroundingPositions(posInfo.pos)){
						nextPos.add(new PosInfo(neighbour, posInfo.movesLeft-1));
					}
				}
			}
		}
		//Remove original position
		validMoves.remove(pos);
		return validMoves;
	}

	/**
	 * Used for iterative flood on positions of board
	 * @author gillcall1
	 *
	 */
	private class PosInfo{
		int movesLeft;
		Position pos;

		public PosInfo(Position pos, int movesLeft){
			this.pos=pos;
			this.movesLeft = movesLeft;
		}
	}

	/**
	 * Gets surrounding positions including rooms
	 * @param pos
	 * @return
	 */
	private List<Position> getSurroundingPositions(Position pos){
		if(pos.isRoom()){
			return pos.getRoom().getEntrances();
		}
		List<Position> adjPos = new ArrayList<Position>();
		int x = pos.getX();
		int y = pos.getY();
		int[] xCoords = new int[]{x+1,x,x-1,x};
		int[] yCoords = new int[]{y,y+1,y,y-1};
		//Checks adjTiles
		for(int i = 0; i<xCoords.length; i++){
			if(!(xCoords[i]<0||xCoords[i]>=board.length||yCoords[i]<0||yCoords[i]>=board[0].length)){
				if(!(board[xCoords[i]][yCoords[i]] instanceof RoomSquare)){
					adjPos.add(new Position(xCoords[i],yCoords[i]));
				}
			}
		}
		if(board[x][y] instanceof DoorSquare){
			adjPos.add(new Position(((DoorSquare)board[x][y]).to()));
		}
		return adjPos;
	}

	/**
	 * Creates the rooms and passages between them
	 */
	private void createRooms(){
		//Creates rooms
		for(int i = 0; i<roomTitles.length; i++){
			rooms.add(new Room(roomTitles[i], null, new ArrayList<Position>(),roomCoordinates[i][0],roomCoordinates[i][1]));
		}
		//Create passages
		//Kitchen->Study
		rooms.get(0).setPassage(rooms.get(8));
		//Study->Kitchen
		rooms.get(8).setPassage(rooms.get(0));
		//Conservatory->Lounge
		rooms.get(2).setPassage(rooms.get(6));
		//Lounge->Conservatory
		rooms.get(6).setPassage(rooms.get(2));
	}

	/**
	 * Setup the entrances for the rooms
	 */
	private void setupEntrances(){
		//Setup entrances
		for(int x = 0; x<board.length; x++){
			for(int y = 0; y<board[0].length; y++){
				if(board[x][y] instanceof DoorSquare){
					((DoorSquare)board[x][y]).to().addEntrance(new Position(x,y));;
				}
			}
		}
	}

	/**
	 * Draws the board and players
	 */
	public BufferedImage draw(){
		BufferedImage boardReturn = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)boardReturn.getGraphics();
		g.drawImage(boardImage, 0, 0, 1024, 1024, null);

		for (Player p : playerPos.keySet()){
			Position pos = playerPos.get(p);
			RadialGradientPaint gp = new RadialGradientPaint(
					OFF_X +4+ pos.getX()*GRID_SIZE + GRID_SIZE / 2.f, 
					OFF_Y +4+ pos.getY()*GRID_SIZE + GRID_SIZE / 2.f, 
					GRID_SIZE/2.f+32, 
					new float[] {0.f, 1.f}, 
					new Color[]{new Color(255, 255, 255, 150), new Color(255, 255, 255, 0)}, CycleMethod.NO_CYCLE);

			g.setPaint(gp);
			g.fillRect(OFF_X - 32 + pos.getX()*GRID_SIZE, OFF_Y - 32 + pos.getY()*GRID_SIZE, GRID_SIZE+64, GRID_SIZE+64);
			g.drawImage(playerTokenImage[p.getCharacter().ordinal()], OFF_X +4+ pos.getX()*GRID_SIZE, OFF_Y +4+ pos.getY()*GRID_SIZE, null);
		}
		g.setColor(new Color(0,240,255,200));
		g.setStroke(new BasicStroke(3));
		for(Position pos : highlightPositions){
			g.fillRect(OFF_X+(pos.getX()*GRID_SIZE), OFF_Y+(pos.getY()*GRID_SIZE), GRID_SIZE, GRID_SIZE);
		}
		g.setColor(new Color(0,0,0,255));
		for(Position pos : highlightPositions){
			g.drawRect(OFF_X+(pos.getX()*GRID_SIZE), OFF_Y+(pos.getY()*GRID_SIZE), GRID_SIZE, GRID_SIZE);
		}
		return boardReturn;
	}

	/**
	 * Highlights a square at a position
	 * @param pos
	 */
	public void highlightSquare(Position pos) {
		highlightPositions.add(pos);
	}

	/**
	 * Highlights all valid moves for a player wit given roll
	 * @param pos
	 */
	public void highlightValidMoves(Player player, int roll) {
		Set<Position> positions = getValidMoves(playerPos.get(player), roll);
		for(Position pos : positions){
			if(pos.isRoom()){
				for(int x = 0; x<board.length; x++){
					for(int y = 0; y<board[0].length; y++){
						if(board[x][y] instanceof RoomSquare && ((RoomSquare)board[x][y]).room()!=null){
							if(((RoomSquare)board[x][y]).room().equals(pos.getRoom())){
								highlightPositions.add(new Position(x,y));
							}
						}
					}
				}
			}else{
				highlightPositions.add(pos);
			}
		}

	}

	/**
	 * Clears all highlighted positions
	 */
	public void clearHighlights() {
		highlightPositions.clear();
	}

	/**
	 * Changes a coordinate pos to a room pos if is in room else return null
	 * @param newPos
	 * @return
	 */
	public Position coordinatePosToRoomPos(Position newPos) {
		if(board[newPos.getX()][newPos.getY()] instanceof RoomSquare && ((RoomSquare)board[newPos.getX()][newPos.getY()]).room()!=null){
			return new Position(((RoomSquare)board[newPos.getX()][newPos.getY()]).room());
		}
		return null;
	}
}
