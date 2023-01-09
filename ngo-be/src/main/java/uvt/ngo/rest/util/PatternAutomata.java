package uvt.ngo.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for pattern finding.
 * Main purpose is to serve in finding
 * forbidden words within the NGO interface
 */
public class PatternAutomata {

    final static Logger logger = LoggerFactory.getLogger(PatternAutomata.class);

    final static int NO_OF_CHARS = 256;

    /**
     * Logs all occurences and returns if the paterns was found(at least once)
     * @param pattern
     * @param text
     * @return
     */
    public static boolean search(final char[] pattern,final  char[] text)
    {
        int M = pattern.length;
        int N = text.length;

        int[][] TF = new int[M+1][NO_OF_CHARS];

        computeAutomata(pattern, M, TF);

        boolean hasForbidden = false;

        // Process txt over FA.
        int i, state = 0;
        for (i = 0; i < N; i++)
        {
            state = TF[state][text[i]];
            if (state == M) {
                logger.info("Forbidden word: {}", pattern);
                hasForbidden = true;
            }
        }
        return hasForbidden;
    }

    /**
     * find a possible state(next state)
     * @param pattern
     * @param M
     * @param stare
     * @param x
     * @return
     */
    static int nextState(final char[] pattern, final int M, final int stare, final int x)
    {

        // If the character c is same as next character in pattern,then simply increment stare
        if(stare < M && x == pattern[stare])
            return stare + 1;

        // ns stores the result which is next stare
        int ns, i;

        // ns finally contains the longest prefix which is also suffix in "pattern[0..stare-1]c"

        // Start from the largest possible value and stop when you find a prefix which is also suffix
        for (ns = stare; ns > 0; ns--)
        {
            if (pattern[ns-1] == x)
            {
                for (i = 0; i < ns-1; i++)
                    if (pattern[i] != pattern[stare-ns+1+i])
                        break;
                if (i == ns-1)
                    return ns;
            }
        }

        return 0;
    }

    /**
     * resolve automata
     * @param pattern
     * @param M
     * @param automata
     */
    static void computeAutomata(char[] pattern, int M, int automata[][])
    {
        int state, x;
        for (state = 0; state <= M; ++state)
            for (x = 0; x < NO_OF_CHARS; ++x)
                automata[state][x] = nextState(pattern, M, state, x);
    }


}