class Solution {
    public int numIslands(char[][] grid) {
        int n = grid.length, m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        int ans = 0;

        for(int i =0; i < n ; i++)
        {
            for(int j = 0; j < m; j++)
            {
                if(!visited[i][j] && grid[i][j] == '1')
                {
                    dfs(grid, i, j, m, n, visited);
                    ans++;
                }
            }
        }
        return ans;
    }

    static void dfs(char[][] grid, int i, int j, int m, int n, boolean[][] visited)
    {
        if(i > -1 && j > -1 && i < n && j < m && !visited[i][j] && grid[i][j] == '1')
        { 
            visited[i][j] = true;
            dfs(grid, i+1, j, m, n, visited);
            dfs(grid, i, j+1, m, n, visited);
            dfs(grid, i-1, j, m, n, visited);
            dfs(grid, i, j-1, m, n, visited);
        }
    }
}