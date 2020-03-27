Rival Chess Model
=================

Chess board and move utilities.

My first chess program was Rival for DOS, written while listening to Metallica's newly-released Black Album, a few years back in the early nineties.
It was pretty good and pretty fast, but not very well written. Rival, that is, not the Black Album.

Next up was Rival for Windows a couple of years later, again in C++ (Borland). Then, Rival for Android was released which
used the Rival Chess Engine written in Java.

None of the code was particularly well-written.  It's because I didn't care.

This is better. It's a well-tested library of chess utilities with an expressive interface and simple code.

It's not a fast as the Rival Chess engine's internal move generation and board manipulation routines. Those use magic bitboards and are at least
20x faster than this library.

### Installation

#### Maven

    <dependency>
        <groupId>com.netsensia.rivalchess</groupId>
        <artifactId>rivalchess-model</artifactId>
        <version>6.0.0</version>
    </dependency>
    
#### Gradle

    compile group: 'com.netsensia.rivalchess', name: 'rivalchess-model', version: '6.0.0'

### Quick Start

#### Creating a Chess Board

    Board board = Board.fromFen("6k1/6p1/1p2q2p/1p5P/1P3RP1/2PK1B2/1r2N3/8 b - g3 5 56");
    
#### Generating Legal Moves

```   
List<Move> legalMoves = board.getLegalMoves();
```

#### Making a Move
```
if (!legalMoves.empty()) {
    Board newBoard = Board.fromMove(board, legalMoves.get(0));
}
```  
``` 
Board newBoard = Board.fromMove(board, Move(Square.E2, Square.E4));
``` 
``` 
Board newBoard = Board.fromMove(board, new Move("e7f8Q"));
``` 
#### Changing Board State

The Board class is immutable. When a move is made, you get a new Board. To change a Board's state without making a move, use the builder.

    // Create a builder with the current board as a base
    BoardBuilder boardBuilder = Board.builder();
    
    // Modify state as required
    boardBuilder.withSquareOccupant(Square.E2, SquareOccupant.NONE);
    boardBuilder.withSquareOccupant(Square.E4, SquareOccupant.WP);
    
    // Create new board
    Board newBoard = boardBuilder.build();
    
    // Or do it all in one
    Board newBoard = Board.builder()
            .withSquareOccupant(Square.E2, SquareOccupant.NONE)
            .withSquareOccupant(Square.E4, SquareOccupant.WP)
            .build()
            
    SquareOccupant squareOccupant = newBoard.getSquareOccupant(Square.E4);
    Piece piece = squareOccupant.getPiece(); // == Piece.PAWN
            
##### The Square and Direction Enums

    final Square square = Square.C6;
    
    for (final SliderDirection sliderDirection : SliderDirection.values()) {
        if (square.isValidDirection(sliderDirection)) {
            final Square newSquare = square.fromDirection(sliderDirection);
            ...
        }
    }
    
    KnightDirection knightDirection = KnightDirection.EN; // Two steps east, one step north
    
    if (square.isValidDirection(knightDirection)) {
        final Square newSquare = square.fromDirection(knightDirection);
        ...
    }
        
#### Is Mover In Check?

    boolean isCheck = board.isCheck();
        
#### Some Other Things

    Colour mover = board.getSideToMove();

    Colour opponent = mover.opponent();
    
    boolean canCastle = board.isKingSideCastleAvailable(Colour.WHITE);

    List<Square> blackKnightSquares = board.getSquaresWithOccupant(SquareOccupant.BN);

    boolean isSquareAttacked = board.isSquareAttackedBy(Square.A7, Colour.BLACK)
    
    squareOccupant.WB.ofColour(Colour.BLACK); // == SquareOccupant.BB
    

