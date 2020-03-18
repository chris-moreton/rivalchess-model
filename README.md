Rival Chess Model
=================

Chess board utilities. 

This library was written for use by the [Rival Chess engine](https://github.com/chris-moreton/rivalchess-engine).

These aren't fast enough to be used for generation during search - the Rival engine uses magic bitboards for that.

But these are very useful, brutally tested and provide an expressive interface when speed isn't the primary concern.

Some examples are shown below but you can discover the rest of the API easily enough.

#### Maven

    <dependency>
        <groupId>com.netsensia.rivalchess</groupId>
        <artifactId>rivalchess-model</artifactId>
        <version>4.1.1</version>
    </dependency>
    
#### Gradle

    compile group: 'com.netsensia.rivalchess', name: 'rivalchess-model', version: '4.1.1'

#### Managing a Chess Board

    Board board = Board.fromFen("6k1/6p1/1p2q2p/1p5P/1P3RP1/2PK1B2/1r2N3/8 b - g3 5 56");
    
    // Place a white rook on E2
    board.setSquareOccupant(Square.E2, SquareOccupant.WR);
    
    SquareOccupant squareOccupant = board.getSquareOccupant(Square.E2);
    Piece piece = squareOccupant.getPiece(); // == Piece.ROOK
    
    squareOccupant.WB.ofColour(Colour.BLACK); // == squareOccupant.BB
    
    List<Square> blackKnightSquares = BoardUtils.getSquaresWithOccupant(board, SquareOccupant.BN);

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

