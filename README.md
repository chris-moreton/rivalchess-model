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
        <version>5.0.0</version>
    </dependency>
    
#### Gradle

    compile group: 'com.netsensia.rivalchess', name: 'rivalchess-model', version: '5.0.0'

#### Managing a Chess Board

    Board board = Board.fromFen("6k1/6p1/1p2q2p/1p5P/1P3RP1/2PK1B2/1r2N3/8 b - g3 5 56");
    
The Board class is immutable. This allows for some parallel stream processing when generating moves.
To modify a Board, create a new one using the builder.

    // Place a white pawn on E4
    Board.BoardBuilder boardBuilder = new Board.BoardBuilder(board);
    boardBuilder.withSquareOccupant(Square.E2, SquareOccupant.NONE);
    boardBuilder.withSquareOccupant(Square.E4, SquareOccupant.WP);
    
    SquareOccupant squareOccupant = board.getSquareOccupant(Square.E4);
    Piece piece = squareOccupant.getPiece(); // == Piece.PAWN
        
Or, get a new Board by making a move.

    Board newBoard = Board.fromMove(board, Move(Square.E2, Square.E4));
    Board newBoard = Board.fromMove(board, new Move("e7f8Q"));
    
#### Get Legal Moves

    List<Move> legalMoves = board.getLegalMoves();
    
#### Some Other Things

    boolean isCheck = board.isCheck();
    Colour mover = board.getSideToMove();
    
    Colour opponent = mover.opponent();
    
    boolean canCastle = board.isKingSideCastleAvailable(Colour.WHITE);

    List<Square> blackKnightSquares = board.getSquaresWithOccupant(SquareOccupant.BN);

    boolean isSquareAttacked = board.isSquareAttackedBy(Square.A7, Colour.BLACK)
    
    squareOccupant.WB.ofColour(Colour.BLACK); // == squareOccupant.BB
