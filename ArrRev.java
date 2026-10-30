import java.util.stream.IntStream;

public class ArrRev {
    public static char[] rv(char[] a){
        int n = a.length;
        return IntStream.range(0, n)
                .map(i -> a[n - 1 - i])
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                )
                .toString()
                .toCharArray();
    }
}
