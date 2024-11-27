package app.view;

// Import required libraries and imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import app.entity.Event.CommonEvent;

// Import applications specific classes for map functionality and JXMapViewer
import app.interface_adapter.display_event.DisplayEventController;
import app.interface_adapter.home.HomeController;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.display_event.DisplayEventInteractor;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.*;
import java.awt.geom.Point2D;

import static java.lang.Math.pow;

public class HomeView extends JPanel implements PropertyChangeListener {
    // Constant for the view name for navigation purposes
    private static final String VIEW_NAME = "Home";

    // View model required for managing logic for view
    private final HomeViewModel homeViewModel;
    private final DisplayEventController displayEventController;

    private JPanel parentPanel;

    private HomeController homeController;
    // list of events
    private ArrayList<CommonEvent> events;

    // Buttons for zooming in, out, logging out, and creating an event
    private final JButton zoomInButton;
    private final JButton zoomOutButton;
    private final JButton logOutButton;
    private final JButton filterButton;
    private final JButton createEventButton;

    // Double declaring the zoom level, which is adjusted later in the code
    private double ZOOM_LEVEL;

    // Map viewer to display the map
    private JXMapViewer mapViewer;

    /**
     * Constructor for the HomeView class to set up the view model,
     * swing components, and map components.
     * @param homeViewModel .
     * */
    public HomeView(HomeViewModel homeViewModel, DisplayEventController displayEventController) {
        this.homeViewModel = homeViewModel;
        this.displayEventController = displayEventController;
        this.events = displayEventController.execute();
        // Add view as listener
        this.homeViewModel.addPropertyChangeListener(this);

        // Create buttons with labels and event handles
        zoomInButton = createButton("Zoom in", evt -> handleZoomInAction());
        zoomOutButton = createButton("Zoom out", evt -> handleZoomOutAction());
        logOutButton = createButton("Log out", evt -> handleLogoutAction());
        filterButton = createButton("Filter", evt -> handleFilterAction());
        createEventButton = createButton("Create event", evt -> handleEventAction());

        // Set panel layout
        setLayout(new BorderLayout());

        // Set up view components
        setupView();
    }

    /**
     * Function that sets parent panel in order for navigation
     * between views to be possible.
     * @param parentPanel .
     * */
    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    /**
     * Function that draws and sets up the map viewer, sidebar, and
     * zoom panel.
     * */
    private void setupView() {
        // Set up the map viewer
        mapViewer = setupMapViewer();

        // Create sidebar by initializing a new JPanel
        JPanel sidebar = new JPanel();

        // Set layout for sidebar
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Add required buttons
        sidebar.add(logOutButton);
        sidebar.add(createEventButton);
        sidebar.add(filterButton);

        // set the sidebar to be at the screen's left
        add(sidebar, BorderLayout.WEST);

        // Create zoom panel using the same logic as above
        JPanel zoomPanel = new JPanel();
        zoomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        zoomPanel.add(zoomInButton);
        zoomPanel.add(zoomOutButton);

        // Set the zoom panel to be at the bottom of the screen
        add(zoomPanel, BorderLayout.SOUTH);

        // Set the map to be at the center of the screen
        add(mapViewer, BorderLayout.CENTER);
    }

    /**
     * Create and set up the JXMapViewer and its
     * initial properties. Includes drawing all the waypoints
     * to the map.
     * */
    private JXMapViewer setupMapViewer() {
        // Instantiate JXMapViewer
        JXMapViewer mapViewer = new JXMapViewer();

        // Map information using tile factory to use OpenStreetMap's built-in tiles
        TileFactoryInfo info = new TileFactoryInfo(
                1, 17, 17, 256, true, true,
                "https://tile.openstreetmap.org/", "x", "y", "z") {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                return this.baseURL + (17 - zoom) + "/" + x + "/" + y + ".png";
            }
        };

        // Make a tile factory for the map viewer to use
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Get campus coordinates for UofT and store it as a GeoPosition
        GeoPosition uoftCampus = new GeoPosition(43.6629, -79.3957);

        // Set the map's default zoom level
        mapViewer.setZoom(1);

        // Set the map's default location to be the UofT campus
        mapViewer.setAddressLocation(uoftCampus);

        // Add markers to the map
        Set<DefaultWaypoint> waypoints = new HashSet<>();
        Map<DefaultWaypoint, Color> waypointColors = new HashMap<>();
        Map<DefaultWaypoint, String> waypointTitles = new HashMap<>();
        for (CommonEvent event : events) {
            Color colour = Color.CYAN;
            double latitude = event.getLatitude();
            double longitude = event.getLongitude();
            String title = event.getTitle();
            // Had to cast to arraylist bc for some reason list<string> doesn't work
            ArrayList<String> tags = (ArrayList<String>) event.getTags();
            // Colour code events based off of tags
            for (String tag : tags) {
                switch (tag) {
                    case "Gaming":
                        colour = Color.GREEN;
                        break;
                    case "Music":
                        colour = Color.BLACK;
                        break;
                    case "Sports":
                        colour = Color.CYAN;
                        break;
                    case "Art and Culture":
                        colour = Color.ORANGE;
                        break;
                    case "Education":
                        colour = Color.PINK;
                        break;
                    case "Travel":
                        colour = Color.GRAY;
                        break;
                    case "Festival":
                        colour = Color.YELLOW;
                        break;
                }
            }
            // Create the new waypoint
            DefaultWaypoint waypoint = new DefaultWaypoint(new GeoPosition(latitude, longitude));
            waypoints.add(waypoint);
            waypointColors.put(waypoint, colour);
            waypointTitles.put(waypoint, title);

            // Debugging Statements
            System.out.println(latitude);
            System.out.println(longitude);
        }
        WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();

        // Create waypoint renderer
        waypointPainter.setRenderer((g, map, waypoint) -> {
            Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());

            // Get the color and title
            Color color = waypointColors.getOrDefault(waypoint, Color.BLACK);
            String title = waypointTitles.getOrDefault(waypoint, "");

            // Draw a marker (circle)
            int radius = 6;
            g.setColor(color);
            g.fillOval((int) point.getX() - radius, (int) point.getY() - radius, 2 * radius, 2 * radius);

            // Draw the title
            g.setColor(Color.BLACK);
            g.drawString(title, (int) point.getX() + radius + 2, (int) point.getY());
        });

        // Set the waypoints and add the painter to the map
        waypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(waypointPainter);

        // Enable dragging functionality for the map
        setupDragFunctionality(mapViewer);

        return mapViewer;
    }

    /**
     * Add dragging functionality to the map, so that when a user
     * drags the map the screen follows the cursor and centralizes
     * the map to show the dragged location.
     * @param mapViewer, the map viewer object.
     * */
    private void setupDragFunctionality(JXMapViewer mapViewer) {
        // Start tracking the cursor's location by setting it to nothing
        final Point[] lastMousePosition = {null};

        // Record the cursor's position by tracking when the mouse is pressed
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMousePosition[0] = e.getPoint();
            }
        });
        // User dragging screen makes the map move:
        mapViewer.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // First, adjust tiles scrolled based off of user's zoom level.
                System.out.println(mapViewer.getZoom());
                if(mapViewer.getZoom() <= 5){
                    ZOOM_LEVEL = 0.000025;
                } else if (mapViewer.getZoom() <= 7){
                    ZOOM_LEVEL = 0.00025;
                } else if (mapViewer.getZoom() <= 9){
                    ZOOM_LEVEL = 0.001;
                } else {
                    ZOOM_LEVEL = 0.001 * mapViewer.getZoom();
                }
                // If the user moved their mouse, track the distance
                if (lastMousePosition[0] != null) {
                    Point currentMousePosition = e.getPoint();
                    // Record the change in X coordinate
                    int deltaX = currentMousePosition.x - lastMousePosition[0].x;

                    // Record the change in Y coordinate
                    int deltaY = currentMousePosition.y - lastMousePosition[0].y;
                    GeoPosition currentPosition = mapViewer.getAddressLocation();

                    // Adjust the location based off of the change in coordinates and zoom level
                    double longitudeDelta = -deltaX * ZOOM_LEVEL;
                    double latitudeDelta = deltaY * ZOOM_LEVEL;

                    // Update the location displayed on the user's screen
                    GeoPosition newPosition = new GeoPosition(
                            currentPosition.getLatitude() + latitudeDelta,
                            currentPosition.getLongitude() + longitudeDelta
                    );

                    // Update the map viewer's address location
                    mapViewer.setAddressLocation(newPosition);
                    lastMousePosition[0] = currentMousePosition;
                }
            }
        });
    }
    /**
     * Function to zoom in on the map by
     * decreasing mapViewer's zoom level
     * */
    private void handleZoomInAction() {
        int currentZoom = mapViewer.getZoom();
        if (currentZoom > 1) {
            mapViewer.setZoom(currentZoom - 1);
        }
    }

    /**
     * Function to zoom out on the map by
     * increasing mapViewer's zoom level
     * */
    private void handleZoomOutAction() {
        int currentZoom = mapViewer.getZoom();
        if (currentZoom < 17) {
            mapViewer.setZoom(currentZoom + 1);
        }
    }

    /**
     * Function to switch screens to the create
     * event screen when the create event button
     * is pressed
     * */
    private void handleEventAction(){
        homeController.switchToCreateEventView();
    }

    /**
     * Function to switch screens to the signup
     * screen whenever the logout button is pressed
     * */
    private void handleLogoutAction() {
        homeController.switchToLoginView();
    }

    private void handleFilterAction() {
//        System.out.println("filter button test");
        homeController.switchToFilterEventView();
    }

    /**
     * Function to navigate from this screen to a different screen
     * using the name of the new screen.
     * */


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // don't need any property change, just need to override because of IntelliJ
    }

    /**
     * Function to create a button and set its desired
     * properties like size, colour, font, and interactions
     * @param text, text you want the button to display
     * @param actionListener, desired action listener
     **/
    private JButton createButton(String text, java.awt.event.ActionListener actionListener) {
        // Create a new button
        JButton button = new JButton(text);

        // Set desired dimenstions
        button.setPreferredSize(new Dimension(120, 40));
        button.setMaximumSize(new Dimension(120, 40));

        // Set desired colour scheme and appearance
        button.setBackground(Color.decode("#48BF67"));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));

        // Add an action listener for the button
        button.addActionListener(actionListener);

        // Add hover effect to button by making the background darker when clicked
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(Color.decode("#2E7A46"));
            }
            // Set the background back to its original colour after hovering
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.decode("#48BF67"));
            }
        });
        return button;
    }
    /**
     * Function to return the view name of homeview.
     * */
    public String getViewName() {
        return VIEW_NAME;
    }

    public void setHomeController(HomeController controller) {
        this.homeController = controller;
    }

}
