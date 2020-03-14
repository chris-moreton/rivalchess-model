package com.netsensia.rivalchess.model;

public class Square implements Comparable {
    private int xFile;
	private int yRank;

    public Square(int xFile, int yRank) {
        this.set(xFile, yRank);
    }

    public static Square fromAlgebraic(String algebraic) {
        return fromAlgebraic(algebraic, 8);
    }

    public static Square fromAlgebraic(String algebraic, int boardRanks) {
        if (algebraic.length() != 2) {
            throw new RuntimeException("Invalid algebraic square " + algebraic);
        }
        final int x = algebraic.toCharArray()[0] - 97;
        final int y = boardRanks - (algebraic.toCharArray()[1] - 48);

        return new Square(x,y);
    }

    public int getXFile() {
        return this.xFile;
    }

    public int getYRank() {
        return this.yRank;
    }

    public void set(int xFile, int yRank) {
        this.xFile = xFile;
        this.yRank = yRank;
    }

    public char getAlgebraicXFile() {
        return (char) (97 + this.getXFile());
    }

    public char getAlgebraicYRank() {
        return getAlgebraicYRank(8);
    }

    public char getAlgebraicYRank(int boardRanks) {
        return Character.forDigit((boardRanks - this.getYRank()), 10);
    }

    public String getAlgebraic(int boardFiles) {
        return "" + this.getAlgebraicXFile() + getAlgebraicYRank(boardFiles);
    }

    @Override
    public String toString() {
        return " " + this.getXFile() + ":" + this.getYRank();
    }

	@Override
	public boolean equals(Object o) {
        if (o instanceof Square) {
            Square br = (Square) o;
            return (br.getXFile() == this.xFile && br.getYRank() == this.yRank);
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
	public int hashCode() {
		return this.xFile * 8 + this.yRank;
	}
}
