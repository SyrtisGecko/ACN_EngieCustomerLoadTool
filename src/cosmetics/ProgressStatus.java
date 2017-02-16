package cosmetics;

import java.awt.*;

/**
 * Created by Przemek on 2017-02-16.
 */

public class ProgressStatus {

    public final static ProgressStatus NA = new ProgressStatus("N/A", Color.BLACK);

    public final static ProgressStatus SELECTED = new ProgressStatus("Selected...", Color.DARK_GRAY);

    public final static ProgressStatus LOADING = new ProgressStatus("Loading...", Color.BLUE);

    public final static ProgressStatus SAVING = new ProgressStatus("Saving...", Color.ORANGE);

    public final static ProgressStatus PROCESSING = new ProgressStatus("Processing...", Color.WHITE);

    public final static ProgressStatus ERROR = new ProgressStatus("<<Error>>", Color.RED);

    public final static ProgressStatus DONE = new ProgressStatus("Done!", Color.GREEN);


    private Color color;
    private String status;

    public ProgressStatus(String status, Color color) {
        this.status = status;
        this.color = color;
    }

    public ProgressStatus(String status, int r, int g, int b) {
        this.status = status;
        color = new Color(r, g, b);
    }

    public Color getColor() {
        return color;
    }

    public String getStatus() {
        return status;
    }
}
