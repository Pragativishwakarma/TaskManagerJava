package project3;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.sql.*;
import java.util.ArrayList;

public class TaskManagerUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private ArrayList<Task> tasks = new ArrayList<>();
    private JProgressBar progressBar;
    private JLabel totalLbl, completeLbl, overdueLbl;
    private TableRowSorter<DefaultTableModel> sorter;

    public TaskManagerUI() {

        setTitle("Smart Task Manager - Dashboard");
        setSize(1250,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createSidebar();
        createTopBar();
        createTable();
        createBottomPanel();

        // Load tasks from DB on startup
        loadTasksFromDB();
    }

    // ================= SIDEBAR =================
    private void createSidebar(){

        JPanel side = new JPanel();
        side.setBackground(new Color(25,25,35));
        side.setPreferredSize(new Dimension(230,0));
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBorder(BorderFactory.createEmptyBorder(30,15,30,15));

        JLabel logo = new JLabel("TASK MANAGER");
        logo.setForeground(new Color(138,43,226));
        logo.setFont(new Font("Segoe UI",Font.BOLD,18));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        side.add(logo);
        side.add(Box.createRigidArea(new Dimension(0,30)));

        String[] names = {"Add Task","Edit Task","Mark Complete","Delete Task"};

        for(String name : names){
            JButton btn = createStyledButton(name);
            side.add(btn);
            side.add(Box.createRigidArea(new Dimension(0,15)));

            switch(name){
                case "Add Task" -> btn.addActionListener(e->addTask());
                case "Edit Task" -> btn.addActionListener(e->editTask());
                case "Mark Complete" -> btn.addActionListener(e->completeTask());
                case "Delete Task" -> btn.addActionListener(e->deleteTask());
            }
        }

        add(side,BorderLayout.WEST);
    }

    private JButton createStyledButton(String text){
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(200,40));
        button.setBackground(new Color(60,60,80));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI",Font.BOLD,13));
        button.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));

        button.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent e){
                button.setBackground(new Color(138,43,226));
            }
            public void mouseExited(java.awt.event.MouseEvent e){
                button.setBackground(new Color(60,60,80));
            }
        });

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    // ================= TOP BAR =================
    private void createTopBar(){

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(40,40,55));
        top.setPreferredSize(new Dimension(100,70));
        top.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

        JLabel heading = new JLabel("TASK DASHBOARD");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI",Font.BOLD,22));

        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(250,35));
        search.setBackground(new Color(60,60,80));
        search.setForeground(Color.WHITE);
        search.setCaretColor(Color.WHITE);

        model = new DefaultTableModel(
                new String[]{"Title","Description","Priority","Due Date","Status"},0);

        sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);

        search.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyReleased(java.awt.event.KeyEvent e){
                sorter.setRowFilter(RowFilter.regexFilter(search.getText()));
            }
        });

        top.add(heading,BorderLayout.WEST);
        top.add(search,BorderLayout.EAST);
        add(top,BorderLayout.NORTH);
    }

    // ================= TABLE =================
    private void createTable(){
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI",Font.PLAIN,14));
        table.setBackground(new Color(50,50,70));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(80,80,100));
        table.getTableHeader().setBackground(new Color(138,43,226));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(50,50,70));

        add(scroll,BorderLayout.CENTER);
    }

    // ================= BOTTOM PANEL =================
    private void createBottomPanel(){

        JPanel bottom = new JPanel(new GridLayout(1,4,20,10));
        bottom.setBackground(new Color(40,40,55));
        bottom.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        totalLbl = createStatLabel("Total: 0");
        completeLbl = createStatLabel("Completed: 0");
        overdueLbl = createStatLabel("Overdue: 0");

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBackground(new Color(60,60,80));
        progressBar.setForeground(new Color(138,43,226));

        bottom.add(totalLbl);
        bottom.add(completeLbl);
        bottom.add(overdueLbl);
        bottom.add(progressBar);

        add(bottom,BorderLayout.SOUTH);
    }

    private JLabel createStatLabel(String text){
        JLabel lbl = new JLabel(text,SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI",Font.BOLD,14));
        lbl.setOpaque(true);
        lbl.setBackground(new Color(60,60,80));
        return lbl;
    }

    // ================= TASK FUNCTIONS =================

    private void addTask(){
        JTextField title=new JTextField();
        JTextArea desc=new JTextArea(3,20);
        JComboBox<String> priority=new JComboBox<>(new String[]{"High","Medium","Low"});
        JTextField due=new JTextField("2026-12-31");

        JPanel panel=new JPanel(new GridLayout(0,1,5,5));
        panel.add(new JLabel("Title:")); panel.add(title);
        panel.add(new JLabel("Description:")); panel.add(desc);
        panel.add(new JLabel("Priority:")); panel.add(priority);
        panel.add(new JLabel("Due Date (YYYY-MM-DD):")); panel.add(due);

        int result=JOptionPane.showConfirmDialog(this,panel,"Add Task",JOptionPane.OK_CANCEL_OPTION);

        if(result==JOptionPane.OK_OPTION){
            Task task=new Task(
                    title.getText(),
                    desc.getText(),
                    priority.getSelectedItem().toString(),
                    LocalDate.parse(due.getText())
            );

            tasks.add(task);

            model.addRow(new Object[]{
                    task.getTitle(),
                    task.getDescription(),
                    task.getPriority(),
                    task.getDueDate(),
                    task.getStatus()
            });

            saveTaskToDB(task);
            updateStats();
        }
    }

    private void editTask(){
        int row=table.getSelectedRow();
        if(row==-1) return;

        String newTitle=JOptionPane.showInputDialog("Enter New Title:");
        if(newTitle!=null){
            Task task = tasks.get(row);
            task.setTitle(newTitle);
            model.setValueAt(newTitle,row,0);
            updateTaskInDB(task);
        }
    }

    private void completeTask(){
        int row=table.getSelectedRow();
        if(row==-1) return;

        Task task = tasks.get(row);
        task.setStatus("Completed");
        model.setValueAt("Completed",row,4);
        updateTaskStatusInDB(task);
        updateStats();
    }

    private void deleteTask(){
        int row=table.getSelectedRow();
        if(row==-1) return;

        Task task = tasks.get(row);
        tasks.remove(row);
        model.removeRow(row);
        deleteTaskFromDB(task);
        updateStats();
    }

    private void updateStats(){
        int total=tasks.size();
        int completed=0;
        int overdue=0;

        for(Task t:tasks){
            if(t.getStatus().equals("Completed")) completed++;
            if(t.isOverdue()) overdue++;
        }

        totalLbl.setText("Total: "+total);
        completeLbl.setText("Completed: "+completed);
        overdueLbl.setText("Overdue: "+overdue);

        if(total>0)
            progressBar.setValue((completed*100)/total);
    }

    // ================= DATABASE FUNCTIONS =================
    private void saveTaskToDB(Task task){
        String sql = "INSERT INTO tasks(title, description, priority, due_date, status) VALUES(?,?,?,?,?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){

            pst.setString(1, task.getTitle());
            pst.setString(2, task.getDescription());
            pst.setString(3, task.getPriority());
            pst.setDate(4, java.sql.Date.valueOf(task.getDueDate()));
            pst.setString(5, task.getStatus());
            pst.executeUpdate();

        } catch(Exception e){ e.printStackTrace(); }
    }

    private void loadTasksFromDB(){
        String sql = "SELECT * FROM tasks";
        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            tasks.clear();
            model.setRowCount(0);

            while(rs.next()){
                Task task = new Task(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getDate("due_date").toLocalDate()
                );
                task.setStatus(rs.getString("status"));
                tasks.add(task);

                model.addRow(new Object[]{
                        task.getTitle(),
                        task.getDescription(),
                        task.getPriority(),
                        task.getDueDate(),
                        task.getStatus()
                });
            }

            updateStats();

        } catch(Exception e){ e.printStackTrace(); }
    }

    private void updateTaskStatusInDB(Task task){
        String sql = "UPDATE tasks SET status=? WHERE title=? AND due_date=?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){

            pst.setString(1, task.getStatus());
            pst.setString(2, task.getTitle());
            pst.setDate(3, java.sql.Date.valueOf(task.getDueDate()));
            pst.executeUpdate();

        } catch(Exception e){ e.printStackTrace(); }
    }

    private void updateTaskInDB(Task task){
        String sql = "UPDATE tasks SET title=?, description=?, priority=? WHERE due_date=?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){

            pst.setString(1, task.getTitle());
            pst.setString(2, task.getDescription());
            pst.setString(3, task.getPriority());
            pst.setDate(4, java.sql.Date.valueOf(task.getDueDate()));
            pst.executeUpdate();

        } catch(Exception e){ e.printStackTrace(); }
    }

    private void deleteTaskFromDB(Task task){
        String sql = "DELETE FROM tasks WHERE title=? AND due_date=?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){

            pst.setString(1, task.getTitle());
            pst.setDate(2, java.sql.Date.valueOf(task.getDueDate()));
            pst.executeUpdate();

        } catch(Exception e){ e.printStackTrace(); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskManagerUI().setVisible(true));
    }
}