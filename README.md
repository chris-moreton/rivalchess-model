Rival Chess Model
=================

Chess board utilities.

Some examples are shown below but you can discover the rest of the API easily enough.

#### Creating a Board

    Board board = Board.fromFen("6k1/6p1/1p2q2p/1p5P/1P3RP1/2PK1B2/1r2N3/8 b - g3 5 56");
    
    SquareOccupant = board.getSquareOccupant(Square.E2);
    board.setSquareOccupant(Square.E2, SquareOccupant.WR);
    
    boolean isCheck = board.isCheck();
    Colour mover = board.getSideToMove();
    
    boolean canCastle = board.isKingSideCastleAvailable(Colour.WHITE);
    
#### Making a Move

    Move move = new Move(Square.E2, Square.E4);
    Board newBoard = Board.fromMove(board, move);
    
    Board anotherNewBoard = Board.fromMove(board, new Move("e7f8Q"));
    
#### Get Legal Moves

    List<Move> legalMoves = board.getLegalMoves();


