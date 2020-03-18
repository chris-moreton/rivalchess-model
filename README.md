Rival Chess Model
=================

Chess board utilities.

Some examples are shown below but you can discover the rest of the API easily enough.

#### Managing a Chess Board

    Board board = Board.fromFen("6k1/6p1/1p2q2p/1p5P/1P3RP1/2PK1B2/1r2N3/8 b - g3 5 56");
    
    // Place a white rook on E2
    board.setSquareOccupant(Square.E2, SquareOccupant.WR);
    
    SquareOccupant squareOccupant = board.getSquareOccupant(Square.E2);
    Piece piece = squareOccupant.getPiece(); // == Piece.ROOK
    
    squareOccupant.WB.ofColour(Colour.BLACK); // == squareOccupant.BB
    
#### Making a Move

    Move move = new Move(Square.E2, Square.E4);
    Board newBoard = Board.fromMove(board, move);
    
    Board anotherNewBoard = Board.fromMove(board, new Move("e7f8Q"));
    
#### Get Legal Moves

    List<Move> legalMoves = board.getLegalMoves();
    
#### Some Other Things

    boolean isCheck = board.isCheck();
    Colour mover = board.getSideToMove();
    
    Colour opponent = mover.opponent();
    
    boolean canCastle = board.isKingSideCastleAvailable(Colour.WHITE);


