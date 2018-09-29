package rpg.api.packethandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * A class to store bytes that will be send through an {@link OutputStream} and to read bytes from an {@link InputStream}.
 */
public class ByteBuffer {
	private final ArrayList<Byte> bytes = new ArrayList<>();
	
	private int limit = -1,
		writeCursor = 0,
		readCursor = 0;
	
	public ByteBuffer() {}
	
	public ByteBuffer(byte[] bytes) {
		write(bytes);
	}
	
	public void write(byte b) throws IllegalStateException {
		if(limit > -1 && writeCursor >= limit) throw new IllegalStateException("Reached limit of " + limit + " elements");
		
		bytes.add(b);
		
		writeCursor++;
	}
	
	public void write(byte[] bytes) {
		for(final byte b : bytes)
			write(b);
	}
	
	public void writeInt(int i) {
		write((byte) (i >>> 24));
		write((byte) (i >>> 16));
		write((byte) (i >>> 8));
		write((byte) i);
	}
	
	public void writeChar(char c) {
		writeInt(c);
	}
	
	public void writeShort(short s) {
		write((byte) (s >>> 8));
		write((byte) s);
	}
	
	public void writeLong(long l) {
		write((byte) (l >>> 56));
		write((byte) (l >>> 48));
		write((byte) (l >>> 40));
		write((byte) (l >>> 32));
		write((byte) (l >>> 24));
		write((byte) (l >>> 16));
		write((byte) (l >>> 8));
		write((byte) l);
	}
	
	public void writeString(String s) {
		writeInt(s.length());
		
		for(final char c : s.toCharArray())
			writeChar(c);
	}
	
	public void writeDouble(double d) {
		writeLong(Double.doubleToRawLongBits(d));
	}
	
	public void writeFloat(float f) {
		writeInt(Float.floatToRawIntBits(f));
	}
	
	public void writeBoolean(boolean b) {
		write((byte) (b ? 1 : 0));
	}
	
	public byte read() {
		final byte b = bytes.get(readCursor);
		
		readCursor++;
		
		return b;
	}
	
	public int readInt() {
		return read() << 24 | read() << 16 | read() << 8 | read();
	}
	
	public char readChar() {
		return (char) readInt();
	}
	
	public short readShort() {
		return (short) (read() << 8 | read());
	}
	
	public long readLong() {
		return read() << 56 | read() << 48 | read() << 40 | read() << 32 | read() << 24 | read() << 16 | read() << 8 | read();
	}
	
	public String readString() {
		String s = "";
		final int length = readInt();
		
		for(int i = 0; i < length; i++)
			s += readChar();
		
		return s;
	}
	
	public double readDouble() {
		return Double.longBitsToDouble(readLong());
	}
	
	public float readFloat() {
		return Float.intBitsToFloat(readInt());
	}
	
	/**
	 * Returns all non-written bytes of this {@link ByteBuffer} in form of a byte[].
	 *
	 * @return the {@link byte}[]
	 */
	public byte[] toByteArray() {
		final byte[] byteArray = new byte[bytes.size() - readCursor];
		
		for(int i = readCursor; i < bytes.size(); i++)
			byteArray[i - readCursor] = bytes.get(i);
		
		return byteArray;
	}
	
	/**
	 * Write all non-written bytes of this {@link ByteBuffer} to the {@link OutputStream} 'out'.
	 *
	 * @param  out
	 *                         - the {@link OutputStream} to which it will write
	 * @return             the current instance of this class
	 * @throws IOException
	 *                         if an I/O error occures
	 */
	public ByteBuffer writeToOutputStream(OutputStream out) throws IOException {
		out.write(toByteArray());
		
		return this;
	}
	
	/**
	 * Read all bytes from the {@link InputStream} 'in' and store it in the current instance.
	 *
	 * @param  in
	 *                         - the {@link InputStream} from which it will read
	 * @return             the current instance of this class
	 * @throws IOException
	 *                         if an I/O error occurres.
	 */
	public ByteBuffer readFromInputStream(InputStream in) throws IOException {
		while(in.available() > 0)
			write((byte) in.read());
		
		return this;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public ByteBuffer setLimit(int limit) {
		if(bytes.size() <= limit) this.limit = limit;
		
		return this;
	}
	
	public int getWriteCursor() {
		return writeCursor;
	}
	
	public int getReadCursor() {
		return readCursor;
	}
}
