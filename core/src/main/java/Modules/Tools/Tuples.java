package Modules.Tools;

public class Tuples {
    public static class Pair<T, U>{
        public final T first;
        public final U second;

        public Pair(T first, U second){
            this.first = first;
            this.second = second;
        }
    }

    public static class Triplet<T, U, V>{
        public final T first;
        public final U second;
        public final V third;

        public Triplet(T first, U second, V third){
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }

    public static class Quartet<T, U, V, W>{
        public final T first;
        public final U second;
        public final V third;
        public final W fourth;

        public Quartet(T first, U second, V third, W fourth){
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
        }
    }
}
