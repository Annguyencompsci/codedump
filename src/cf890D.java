import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.TreeSet;

public class cf890D {
	public static void main(String[] args) {
		FastScanner input = new FastScanner(System.in);
		
		int N = input.nextInt();
		
		int[] nxt = new int[26];
		int[] prev = new int[26];
		
		TreeSet<String> set = new TreeSet<>();
		
		int[] corrupt = new int[26];
		Arrays.fill(nxt, -1); Arrays.fill(prev, -1);
		
		for (int i = 0; i < N; i++) {
			String s = input.next();
			for (char c : s.toCharArray())
				corrupt[c - 'a']++;
			for (int j = 0; j < s.length() - 1; j++) {
				int cur = s.charAt(j) - 'a';
				int next = s.charAt(j + 1) - 'a';
				if ((nxt[cur] != -1 && nxt[cur] != next) || (prev[next] != -1 && prev[next] != cur))
					terminate();
				nxt[cur] = next;
				prev[next] = cur;
			}
		}
		
		boolean[] visited = new boolean[26];
		for (int i = 0; i < 26; i++)
			if (corrupt[i] > 0 && prev[i] == -1)
				set.add(acrue(i, nxt, new StringBuilder(), visited));
		for (int i = 0; i < 26; i++)
			if (corrupt[i] > 0 && !visited[i])
				terminate();
		StringBuilder sb = new StringBuilder();
		for (String s : set)
			sb.append(s);
		
		System.out.println(sb.toString());
	}
	
	static String acrue(int v, int[] nxt, StringBuilder sb, boolean[] visited) {
		if (visited[v])
			terminate();
		visited[v] = true;
		sb.append((char) (v + 'a'));
		if (nxt[v] == -1)
			return sb.toString();
		return acrue(nxt[v], nxt, sb, visited);
	}
	
	static void terminate() {
		System.out.println("NO");
		System.exit(0);
	}
	
	// Matt Fontaine's Fast IO
	static class FastScanner {
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public FastScanner(InputStream stream) {
			this.stream = stream;
		}

		int read() {
			if (numChars == -1)
				throw new InputMismatchException();
			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}
			return buf[curChar++];
		}

		boolean isSpaceChar(int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		boolean isEndline(int c) {
			return c == '\n' || c == '\r' || c == -1;
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String next() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}

		String nextLine() {
			int c = read();
			while (isEndline(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isEndline(c));
			return res.toString();
		}
	}
}
