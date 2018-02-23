import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Kosaraju's Algorythm for SCC
 *
 * Created by Wendy and Highly Inspired by GeeksForGeeks
 * source: https://www.youtube.com/watch?v=5wFyZJ8yH9Q
 */
public class Main {

    public static void main(String[] args) {

        // Inisialisasi Matrix
        Integer[][] matrix = new Integer[9][9];
        nullMatrix(matrix);

        // Input Nodes
        matrix[0][1] = 1;
        matrix[1][2] = 1;
        matrix[2][3] = 1;
        matrix[3][0] = 1;
        matrix[2][4] = 1;
        matrix[4][5] = 1;
        matrix[5][6] = 1;
        matrix[6][4] = 1;
        matrix[7][6] = 1;
        matrix[7][8] = 1;

        // Melakukan operasi pengecekan SCC
        List<List<Integer>> listScc = scc(matrix);

        // Output
        for(int i = 0; i < listScc.size(); i++){
            System.out.println(listScc.get(i).toString());
        }
    }

    /**
     * Proses utama dari SCC dimana pertama kita melakukan DFS dan menyimpan hasilnya
     * pada suatu stack dan setelah itu matrix di transpose dan melakukan operasi DFS
     * kembali pada matrix tersebut untuk dilakukan pengecekan apakah ia SCC atau bukan.
     *
     * @param matrix
     * @return
     */
    static List<List<Integer>> scc(Integer[][] matrix){
        boolean[] used = new boolean[matrix.length];
        Stack order = new Stack();
        Integer[][] reversedMatrix = reverseMatrix(matrix);

        // Iterasi pertama dalam melakukan DFS
        for(int i = 0; i < matrix.length; i++){
            if(!used[i]){
                dfs(used, order, matrix, i);
            }
        }

        // Inisialisasi data untuk iterasi kedua
        List<List<Integer>> listScc = new ArrayList<>();
        used = new boolean[matrix.length];
        List<Integer> scc;

        /*
            Iterasi kedua dalam melakukan DFS menggunakan stack hasil sebelumnya.

            Dan pada setiap loop hasil dari scc pada method DFS akan disimpan pada
            listScc
        */
        while(!order.isEmpty()){
            if(!used[(int)order.peek()]){
                scc = new ArrayList<>();
                dfsReverse(used, order, reversedMatrix, (int)order.pop(), scc);
                listScc.add(scc);
            } else {
                order.pop();
            }
        }

        return listScc;
    }

    /**
     * Sebuah method DFS dimana jalur yang telah dilalui akan dinonaktifkan dan akan
     * melanjutkan menuju nodes setelahnya secara rekursif dengan syarat belum pernah
     * dilalui sebelumnya.
     *
     * Dan setiap rekursif berakhir maka hasil akan dimasukkan pada stack.
     *
     * @param used
     * @param order
     * @param matrix
     * @param n
     */
    static void dfs(boolean[] used, Stack order, Integer[][] matrix, int n){
        used[n] = true;
        for(int i = 0; i < matrix[n].length; i++){
            if(matrix[n][i] == 1){
                matrix[n][i] = 0;
                if(!used[i]){
                    dfs(used, order, matrix, i);
                }
            }
        }
        order.add(n);
    }

    /**
     * Sebuah method DFS dimana jalur yang telah dilalui akan dinonaktifkan dan akan
     * melanjutkan menuju nodes setelahnya secara rekursif dengan syarat belum pernah
     * dilalui sebelumnya.
     *
     * Setiap rekursif jalur yang dilalui akan dimasukkan pada list.
     *
     * @param used
     * @param order
     * @param matrix
     * @param n
     */
    static void dfsReverse(boolean[] used, Stack order, Integer[][] matrix, int n, List<Integer> list){
        used[n] = true;
        list.add(n);
        for(int i = 0; i < matrix[n].length; i++){
            if(matrix[n][i] == 1){
                matrix[n][i] = 0;
                if(!used[i]){
                    dfsReverse(used, order, matrix, i, list);
                }
            }
        }
    }

    /**
     * Sebuah method yang berguna untuk mentranspose suatu matrix
     *
     * @param matrix
     * @return
     */
    static Integer[][] reverseMatrix(Integer[][] matrix){
        Integer[][] matrixBaru = new Integer[matrix.length][matrix.length];

        for(int i = 0; i < matrixBaru.length; i++){
            for(int j = 0; j < matrixBaru[i].length; j++){
                matrixBaru[i][j] = matrix[j][i];
            }
        }

        return matrixBaru;
    }

    /**
     * Sebuah method untuk membuat seluruh komponen matriks menjadi 0
     *
     * @param matrix
     */
    static void nullMatrix(Integer[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                matrix[i][j] = 0;
            }
        }
    }
}
