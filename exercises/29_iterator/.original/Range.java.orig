// 先讀同資料夾的 README.md，再動手修改這個檔案。

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Range implements Iterable<Integer> {

    private final int start;
    private final int end;
    private final int step;

    public Range(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {

            private int current = start;

            @Override
            public boolean hasNext() {
                // TODO: 往上數（step > 0）時 current 還沒到 end 就有下一個；
                //       往下數（step < 0）時 current 還大於 end 就有下一個
                return step > 0 ? current < end : current > end
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                // TODO: 回傳目前的值，並把 current 往前推進 step
                return current;
            }
        };
    }
}
