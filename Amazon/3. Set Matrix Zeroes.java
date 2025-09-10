//Time - O(mn)
// Space - O(1)

class Solution {
    public void setZeroes(int[][] matrix) {
        boolean row0 = false;
        int r = matrix.length, c = matrix[0].length;
        for(int i = 0; i < r; i ++)
        {
            for(int j = 0; j < c; j++)
            {
                if(matrix[i][j] == 0)
                {
                    matrix[0][j] = 0;
                    if(i > 0) matrix[i][0] = 0;
                    else row0 = true;
                }
            }
        }

        for(int i = 1; i < r; i++)
        {
            for(int j = 1; j < c; j++)
            {
               if(matrix[i][0] == 0 || matrix[0][j] == 0)
                matrix[i][j] = 0;
            }
        }

        if(matrix[0][0] == 0)
        {
            for(int i = 0;  i < r; i++)
                matrix[i][0] = 0;
        }

        if(row0)
        {
            for(int j = 0;  j < c; j++)
                matrix[0][j] = 0;
        }
    
    }
}

//other apporach is to use 2 arrays to store for col/row should be 0
//Time - O(mn)
// Space - O(m+n)