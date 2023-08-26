package Game;

public enum Cell {
    FlaggedBomb {
        @Override
        public String returnState(){
            return "F";
        }
    },
    ClosedBomb{
        @Override
        public String returnState(){
            return "X";
        }
    },
    ShownBomb{
        @Override
        public String returnState(){
            return "B";
        }
    },
    FlaggedField{
        @Override
        public String returnState() {
            return "F";
        }
    },
    ClosedField{
        @Override
        public String returnState() {
            return "X";
        }
    },
    OpenField{
        @Override
        public String returnState() {
            return "O";
        }
    },
    One{
        @Override
        public String returnState() {
            return "1";
        }
    },
    Two{
        @Override
        public String returnState() {
            return "2";
        }
    },
    Three{
        @Override
        public String returnState() {
            return "3";
        }
    },
    Four{
        @Override
        public String returnState() {
            return "4";
        }
    },
    Five{
        @Override
        public String returnState() {
            return "5";
        }
    },
    Six{
        @Override
        public String returnState() {
            return "6";
        }
    },
    Seven{
        @Override
        public String returnState() {
            return "7";
        }
    },
    Eight{
        @Override
        public String returnState() {
            return "8";
        }
    };

    abstract public String returnState();
}
