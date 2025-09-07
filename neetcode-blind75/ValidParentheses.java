class Solution {
    public boolean isValid(String s) 
    {
        Stack<Character> stack = new Stack<Character>();

        for(Character ch : s.toCharArray())
        {
            if(!stack.isEmpty() && counterPart(ch, stack.peek()))
            {
                stack.pop();
            }
            else
            stack.push(ch);
        }
        return stack.isEmpty();
    }

    boolean counterPart(char right, char left)
    {
        if(left == '(' && right == ')')
        {
            return true;
        }
        if(left == '[' && right == ']')
        {
            return true;
        }
        if(left == '{' && right == '}')
        {
            return true;
        }
        return false;
    }
}
