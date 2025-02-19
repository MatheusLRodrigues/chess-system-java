package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// down
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// northwest (nw)
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// northeast (ne)
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// sw
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		// se
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// #SpecialMove RookCastling

		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// #SpecialMove castling KingSide Rook
			Position positionRook1 = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(positionRook1)) {
				Position farRightPosition = new Position(position.getRow(), position.getColumn() + 1);
				Position rightPosition = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(farRightPosition) == null && getBoard().piece(rightPosition) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			// #SpecialMove castling QueenSide Rook
			Position positionRook2 = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(positionRook2)) {
				Position farLeftPosition = new Position(position.getRow(), position.getColumn() - 1);
				Position leftPosition = new Position(position.getRow(), position.getColumn() - 2);
				Position centerLeftPosition = new Position(position.getRow(), position.getColumn() - 3);
 				if (getBoard().piece(farLeftPosition) == null && getBoard().piece(leftPosition) == null
						&& getBoard().piece(centerLeftPosition) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}

		}

		return mat;
	}

}
