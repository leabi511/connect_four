public class minimax extends AIModule
{   
    int player1 = -1;
    int player2 = -1;
    int localChosenMove = -1;
    int width = 0;
    int height = 0;
    
	public void getNextMove(GameStateModule state)
	{
        width = state.getWidth();
        height = state.getHeight();
        player1 = state.getActivePlayer();
        player2 = 3 - player1;
        
        int depthLimit = 5;
        
        GameStateModule game = state.copy();
        for (int i = 0; i < width; i++) {
            if (game.canMakeMove(i))
                game.makeMove(i); //max makes a move
            else
                continue;
            if (game.isGameOver() && game.getWinner() == player1) {//if max wins or draw
                chosenMove = i;
                return;
            }
            game.unMakeMove();
        }
        
        game = state.copy();
        while (!terminate) {
            MaxValue(game, 0, depthLimit);
            depthLimit++;
            if (!terminate)
                chosenMove = localChosenMove;
        }
       
	}
    
    int MaxValue(GameStateModule state, int depth, int depthLimit) {
        if (state.isGameOver() || depth == depthLimit || terminate)
            return evalFunc(state);
        
        int value = -Integer.MAX_VALUE;
        GameStateModule game = state.copy();
        for (int i = 0; i < width; i++) { 
            if (game.canMakeMove(i)) {
                game.makeMove(i); 
            }
            else
                continue;
            int result = MinValue(game, depth+1, depthLimit);
            if (value < result) {
                value = result;
                if (depth == 0) {
                    localChosenMove = i;
                }
            }
            game.unMakeMove(); //unmake max's move
        }
        return value;
    }
        
    int MinValue(GameStateModule state, int depth, int depthLimit) {
        if (state.isGameOver() || depth == depthLimit || terminate)
            return evalFunc(state);
        
        int value = Integer.MAX_VALUE;
        GameStateModule game = state.copy();
        for (int i = 0; i < width; i++) {
            if (game.canMakeMove(i)) {
                game.makeMove(i);
            }
            else
                continue;
            value = Math.min(value, MaxValue(game, depth+1, depthLimit));
            game.unMakeMove(); //unmake min's move
        }
        return value;
    }
    
    int evalFunc(GameStateModule state) {
        int score = 0, tile = 0, emptyTileY = 0;
        int count1, count2;
        int [] fiveTiles = new int[5];
        
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (i+3 < width) { //check horizonal tiles
                    count1 = count2 = 0;
                    for (int k = 0; k <= 3; k++) {
                        tile = state.getAt(i+k,j);
                        if (tile == player1)
                            count1++;
                        else if (tile == player2) 
                            count2++;
                        else
                            emptyTileY = j;
                    }
                    int result = checkConnections(count1, count2);
                    score += checkThreat(count1, count2, result, emptyTileY);
                        
                }
                if (j+3 < height) { //check vertical
                    count1 = count2 = 0;
                    for (int k = 0; k <= 3; k++) {
                        tile = state.getAt(i,j+k);
                        if (tile == player1) 
                            count1++;
                        else if (tile == player2)
                            count2++;
                        else
                            emptyTileY = j+k;
                    }
                    score += checkConnections(count1, count2);
                }
                if (i+3 < width && j+3 < height) { //check upper right diagonal
                    count1 = count2 = 0;
                    for (int k = 0; k <= 3; k++) {
                        tile = state.getAt(i+k,j+k);
                        if (tile == player1) 
                            count1++;
                        else if (tile == player2)
                            count2++;
                        else
                            emptyTileY = j+k;
                    }
                    int result = checkConnections(count1, count2);
                    score += checkThreat(count1, count2, result, emptyTileY);
                }
                if (i-3 >= 0 && j+3 < height) { //check upper left diagonal
                    count1 = count2 = 0;
                    for (int k = 0; k <= 3; k++) {
                        tile = state.getAt(i-k,j+k);
                        if (tile == player1) 
                            count1++;
                        else if (tile == player2)
                            count2++;
                        else
                            emptyTileY = j+k;
                    }
                    int result = checkConnections(count1, count2);
                    score += checkThreat(count1, count2, result, emptyTileY);
                }
            }
            
        }
        return score;
    }
    
    private int checkConnections(int count1, int count2) {
        if (count1 > 0 && count2 == 0) {
            switch (count1) {
                case 4:
                    return 32768;
                case 3:
                    return 1024;
                case 2:
                    return 32;
                case 1:
                    return 1;
            }
        }
        else if (count1 == 0 && count2 > 0) {
            switch (count2) {
                case 4:
                    return -32768;
                case 3:
                    return -1024;
                case 2:
                    return -32;
                case 1:
                    return -1;
            }
        }
        return 0;
    }
    
    private int checkThreat(int count1, int count2, int result, int emptyTileY) {
        if (count1 == 3 && count2 == 0){
            if (player1 == 1 && (emptyTileY+1)%2 == 1) 
                return result*3;
            else if (player1 == 2 && (emptyTileY+1)%2 == 0)
                return result*3;
            else
                return result;
        }
        else if (count1 == 0 && count2 == 3){
            if (player2 == 1 && (emptyTileY+1)%2 == 1)
                return result*3;
            else if (player2 == 2 && (emptyTileY+1)%2 == 0)
                return result*3;
            else
                return result;
        }
        else
            return result;
    }
    
}
