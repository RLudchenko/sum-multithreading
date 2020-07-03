import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Main {
    public static final int ELEMENTS_COUNT = 1_000_000;

    public static void main(String[] args) throws InterruptedException {
        List<Long> nums = LongStream.range(0, ELEMENTS_COUNT)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        RecursiveSum recursiveSum = new RecursiveSum(nums);
        long forkStart = System.currentTimeMillis();
        Long forkJoinSum = forkJoinPool.invoke(recursiveSum);
        long forkEnd = System.currentTimeMillis();
        System.out.println("ForkJoin: " + forkJoinSum + ", time: " + (forkEnd - forkStart));

        ExecutorServiceSum executorServiceSum = new ExecutorServiceSum(nums, 4);
        long executorStart = System.currentTimeMillis();
        long sum = executorServiceSum.calculateListSum();
        long executorEnd = System.currentTimeMillis();
        executorServiceSum.shutdown();
        System.out.println("Executor: " + sum + ", time: " + (executorEnd - executorStart));
    }
}
