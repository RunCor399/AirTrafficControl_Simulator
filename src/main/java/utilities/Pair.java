package utilities;

/**
 * A standard generic Pair<X,Y>, with getters, hashCode, equals, and toString well implemented. 
 * 
 * @param <X> the type of the x object.
 * @param <Y> the type of the y object.
 */
public class Pair<X, Y> {

        private final X x;
        private final Y y;

        public Pair(final X x, final Y y) {
                super();
                this.x = x;
                this.y = y;
        }

        public final X getX() {
            return x;
        }

        public final Y getY() {
            return y;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((x == null) ? 0 : x.hashCode());
            result = prime * result + ((y == null) ? 0 : y.hashCode());
            return result;
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("rawtypes")
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Pair other = (Pair) obj;
            if (x == null) {
                if (other.x != null) {
                    return false;
                }
            } else if (!x.equals(other.x)) {
                return false;
            }
            if (y == null) {
                if (other.y != null) {
                    return false;
                }
            } else if (!y.equals(other.y)) {
                return false;
            }
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "Pair [x=" + x + ", y=" + y + "]";
        }


}
