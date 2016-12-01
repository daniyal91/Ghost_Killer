package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a game map, composed of a 2-dimension matrix of tiles. It implements methods for locating
 * different cells on the map and restoring maps from text files.
 *
 * @author SnapDragon
 */
public class GameGrid {

    /**
     * Different types of cases a GameGrid object can hold.
     */
    public static enum CASE_TYPES {
        GRASS, BUSH, ROAD, START, END, NONE
    };

    /**
     * Images used to represent the different types of case types
     */
    public static String[] CASE_TYPES_ICON_PATHS =
                    {"icons/grass.jpg", "icons/grass2.jpg", "icons/road.jpg", "icons/start.png", "icons/end.png"};

    public int pathindex = 1;
    public String filePath = "";

    CASE_TYPES[][] cases;
    private Random randomGenerator = new Random();

    /**
     * Constructs an empty GameGrid.
     * @roseuid 5837CBE90030
     */
    public GameGrid() {
    }

    /**
     * Constructs a new GameGrid with the specified dimensions. All the tiles are initialized as grass.
     *
     * @param lineCount user's choice for width
     * @param columnCount user's choice for length
     */
    public GameGrid(int lineCount, int columnCount) {
        this.cases = new CASE_TYPES[lineCount][columnCount];
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                this.cases[i][j] = CASE_TYPES.GRASS;
            }
        }
    }

    /**
     * Gets the cases used by the grid.
     * @roseuid 5837CBE90873
     */
    public CASE_TYPES[][] getCases() {
        return this.cases;
    }

    /**
     * Sets the cases used by the grid.
     *
     * @param cases the new matrix of cases used by the grid.
     * @roseuid 5837CBE904EA
     */
    public void setCases(CASE_TYPES[][] cases) {
        this.cases = cases;
    }

    /**
     * This method reads a serialized GameGrid object from a file specified by the user.
     *
     * @param filename Name of the file where the object is stored.
     * @param addRandomBushes Determines if random bushes should be generated randomly on the loaded grid.
     *
     * @throws exception If the file could not be read or has the wrong format.
     * @roseuid 5837CBE911AD
     */
    public void readFromFile(String filename, Boolean addRandomBushes) {

        filePath = filename;
        int linenumber = 0; 
        int rows = 0;
        int columns = 0;

        this.cases = new CASE_TYPES[1][1]; //
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String line = null;

            line = br.readLine();
            String[] tokens = line.split("\\s+");
            rows = Integer.parseInt(tokens[0]);
            columns = Integer.parseInt(tokens[1]);

            this.cases = new CASE_TYPES[rows][columns];

            for (int j = 0; j < rows; j++) {

                line = br.readLine();
                tokens = line.split("\\s+");

                for (int i = 0; i < columns; i++) {
                    int caseValue = Integer.parseInt(tokens[i]);
                    this.cases[linenumber][i] = CASE_TYPES.values()[caseValue];
                    if (addRandomBushes && this.cases[linenumber][i] == CASE_TYPES.GRASS
                                    && randomGenerator.nextInt(100) > 92) {
                        this.cases[linenumber][i] = CASE_TYPES.BUSH;
                    }
                }

                linenumber = linenumber + 1; 

            }

            br.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    /**
     * Returns an array representing the connections between entry and exit points.
     *
     * @returns Connectivity Array
     * @roseuid 5837CBE922AC
     */
    public int[][][] connectivities() {
        GridLocation entryPoint = this.entryPoint();
        int[][][] connectivities = new int[this.cases.length][this.cases[0].length][3];
        connectivities[entryPoint.x][entryPoint.y][0] = 1;
        connectivities[entryPoint.x][entryPoint.y][1] = pathindex++;
        this.connect(connectivities, entryPoint.x, entryPoint.y);

        return connectivities;
    }

    /**
     * Gets the cases of the grid corresponding to a certain type.
     *
     * @param caseType Type of the tiles to return.
     *
     * @return an ArrayList of GridLocation of the locations found.
     * @roseuid 5837E9001AD0
     */
    public ArrayList<GridLocation> getCasesByType(CASE_TYPES caseType) {

        ArrayList<GridLocation> response = new ArrayList<GridLocation>();

        for (int i = 0; i < this.cases.length; i++) {
            for (int j = 0; j < this.cases[0].length; j++) {
                if (this.cases[i][j] == caseType) {
                    response.add(new GridLocation(i, j));
                }

            }
        }

        return response;

    }

    /**
     * Returns the entry point of the grid. The entry point is assumed to be at the left edge of the map.
     *
     * @returns the height of the entry point, or -1 if no valid entry point.
     * @roseuid 5837CBE999HB
     */
    public GridLocation entryPoint() {
        return this.getCasesByType(CASE_TYPES.START).get(0);
    }

    /**
     * Returns the path grid.
     *
     * @returns an array list of GridLocation
     * @roseuid 5837CBE99IE3
     */
    public ArrayList<GridLocation> road() {
        return this.getCasesByType(CASE_TYPES.ROAD);
    }

    /**
     * Returns the exit point of the grid. The exit point is assumed to be at the right edge of the map.
     *
     * @returns the height of the exit point, or -1 if no valid exit point.
     * @roseuid 5837CBE99I89
     */
    public GridLocation exitPoint() {
        return this.getCasesByType(CASE_TYPES.END).get(0);
    }

    /**
     * Determines if the location specified is a valid road location.
     *
     * @param line Line of the coordinate to validate.
     * @param column Column of the coordinate to validate.
     * @param connectivities Matrix used for the connectivity check.
     *
     * @return True if the location is a valid road location, false otherwise.
     * @roseuid 5837CBE671AC
     */
    public boolean isRoad(int line, int column, int[][][] connectivities) {

        if (line < 0) {
            return false;
        }
        if (line > this.cases.length - 1) {
            return false;
        }
        if (column < 0) {
            return false;
        }
        if (column > this.cases[0].length - 1) {
            return false;
        }
        if (this.cases[line][column] == CASE_TYPES.GRASS || this.cases[line][column] == CASE_TYPES.BUSH) {
            return false;
        }
        if (connectivities[line][column][0] == 1) {
            return false;
        }
        return true;
    }

    /**
     * Side method for isConnected method, it connects the neighbor of the tile(i,j) together if they are path tiles,
     * from the entrance to the exit point
     * @roseuid 5837CBEIAC45E
     */
    public void connect(int[][][] connectivites, int line, int column) {

        // check the right neighbor
        if (this.isRoad(line, column + 1, connectivites)) {
            connectivites[line][column + 1][0] = 1;
            connectivites[line][column + 1][1] = pathindex++;
            this.connect(connectivites, line, column + 1);
        }

        // check the below neighbor
        if (this.isRoad(line + 1, column, connectivites)) {
            connectivites[line + 1][column][0] = 1;
            connectivites[line + 1][column][1] = pathindex++;
            this.connect(connectivites, line + 1, column);
        }

        // check the above neighbor
        if (this.isRoad(line - 1, column, connectivites)) {
            connectivites[line - 1][column][0] = 1;
            connectivites[line - 1][column][1] = pathindex++;
            this.connect(connectivites, line - 1, column);
        }

        // check the left neighbor
        if (this.isRoad(line, column - 1, connectivites)) {
            connectivites[line][column - 1][0] = 1;
            connectivites[line][column - 1][1] = pathindex++;
            this.connect(connectivites, line, column - 1);
        }

    }
}
