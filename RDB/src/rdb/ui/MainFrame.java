package rdb.ui;

import javax.swing.JList;
import javax.swing.JOptionPane;
import rdb.delegate.MagicDelegate;
import rdb.delegate.ModelDelegate;
import rdb.delegate.TransactionDelegate;
import rdb.model.Agent;
import rdb.model.Coach;
import rdb.model.Contract;
import rdb.model.GeneralManager;
import rdb.model.League;
import rdb.model.Player;
import rdb.model.Plays;
import rdb.model.Team;
import rdb.model.Trade;
import rdb.shared.Errors;

public class MainFrame extends javax.swing.JFrame {
  private static final int UP = 0;
  private static final int GET = UP + 1;
  private static final int LIST = GET + 1;
  private static final int VIEW = LIST + 1;
  private static final int INSERT = VIEW + 1;
  private static final int DELETE = INSERT + 1;
  private static final int AGENT = 0;
  private static final int COACH = AGENT + 1;
  private static final int CONTRACT = COACH + 1;
  private static final int GENERALMANAGER = CONTRACT + 1;
  private static final int LEAGUE = GENERALMANAGER + 1;
  private static final int PLAYER = LEAGUE + 1;
  private static final int PLAYS = PLAYER + 1;
  private static final int TEAM = PLAYS + 1;
  private static final int TRADE = TEAM + 1;
  private static final int SELECT = DELETE + 1;
  private static final int PROJECT = SELECT + 1;
  private static final int JOIN = PROJECT + 1;
  private static final int GROUP = JOIN + 1;
  private static final int HAVING = GROUP + 1;
  private static final int NESTED = HAVING + 1;
  private static final int DIVIDE = NESTED + 1;
  private final ModelDelegate modeller;
  private final TransactionDelegate transactioner;
  private final MagicDelegate magician;
  private int recentError = Errors.NOTHING;

  public MainFrame(ModelDelegate modelDelegate,
      TransactionDelegate transactionDelegate, MagicDelegate magicDelegate) {
    initComponents();
    this.modeller = modelDelegate;
    this.transactioner = transactionDelegate;
    this.magician = magicDelegate;
  }

  private void doCommand(int verb) {
    if (verb >= SELECT) doMagic(verb);
    else
      switch (this.paneMain.getSelectedIndex()) {
        case AGENT:
          doAgent(verb);
          break;
        case COACH:
          doCoach(verb);
          break;
        case CONTRACT:
          doContract(verb);
          break;
        case GENERALMANAGER:
          doGeneralManager(verb);
          break;
        case LEAGUE:
          doLeague(verb);
          break;
        case PLAYER:
          doPlayer(verb);
          break;
        case PLAYS:
          doPlays(verb);
          break;
        case TEAM:
          doTeam(verb);
          break;
        case TRADE:
          doTrade(verb);
          break;
        default:
          return;
      }
    this.labelStatus.setText(this.recentError == Errors.SUCCESS
        ? this.transactioner.getRecentMessage()
        : Errors.parseErrorCode(this.recentError));
  }

  private void doAgent(int verb) {
    Agent model;
    AgentDialog dialog;
    switch (verb) {
      case GET:
        dialog = new AgentDialog(this, null);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified()) {
          this.recentError = this.transactioner.getAgent(dialog.getModel());
          populate(this.listAgents, this.modeller.getAgentList());
        }
        break;
      case LIST:
        this.recentError = this.transactioner.listAgents();
        populate(this.listAgents, this.modeller.getAgentList());
        break;
      case VIEW:
        if (isBlank(this.listAgents)) this.recentError = Errors.LISTSELECT;
        else {
          model = this.modeller
              .getAgentModel(this.listAgents.getSelectedIndex());
          dialog = new AgentDialog(this, model);
          dialog.setVisible(true);
          if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
          else if (dialog.isModified()) {
            this.recentError = this.transactioner.updateAgent(model);
            populate(this.listAgents, this.modeller.getAgentList());
          }
        }
        break;
      case INSERT:
        model = new Agent();
        dialog = new AgentDialog(this, model);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified())
          this.recentError = this.transactioner.insertAgent(model);
        break;
      case DELETE:
        this.recentError = isBlank(this.listAgents) ? Errors.LISTSELECT
            : this.transactioner.deleteAgent(
                this.modeller.getAgentModel(
                    this.listAgents.getSelectedIndex()));
        break;
    }
  }

  private void doCoach(int verb) {
    Coach model;
    CoachDialog dialog;
    switch (verb) {
      case GET:
        dialog = new CoachDialog(this, null);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified()) {
          this.recentError = this.transactioner.getCoach(dialog.getModel());
          populate(this.listCoaches, this.modeller.getCoachList());
        }
        break;
      case LIST:
        this.recentError = this.transactioner.listCoaches();
        populate(this.listCoaches, this.modeller.getCoachList());
        break;
      case VIEW:
        if (isBlank(this.listCoaches)) this.recentError = Errors.LISTSELECT;
        else {
          model = this.modeller
              .getCoachModel(this.listCoaches.getSelectedIndex());
          dialog = new CoachDialog(this, model);
          dialog.setVisible(true);
          if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
          else if (dialog.isModified()) {
            this.recentError = this.transactioner.updateCoach(model);
            populate(this.listCoaches, this.modeller.getCoachList());
          }
        }
        break;
      case INSERT:
        model = new Coach();
        dialog = new CoachDialog(this, model);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified())
          this.recentError = this.transactioner.insertCoach(model);
        break;
      case DELETE:
        this.recentError = isBlank(this.listCoaches) ? Errors.LISTSELECT
            : this.transactioner.deleteCoach(
                this.modeller.getCoachModel(
                    this.listCoaches.getSelectedIndex()));
        break;
    }
  }

  private void doContract(int verb) {
    Contract model;
    ContractDialog dialog;
    switch (verb) {
      case GET:
        dialog = new ContractDialog(this, null);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified()) {
          this.recentError = this.transactioner.getContract(dialog.getModel());
          populate(this.listContracts, this.modeller.getContractList());
        }
        break;
      case LIST:
        this.recentError = this.transactioner.listContracts();
        populate(this.listContracts, this.modeller.getContractList());
        break;
      case VIEW:
        if (isBlank(this.listContracts)) this.recentError = Errors.LISTSELECT;
        else {
          model = this.modeller
              .getContractModel(this.listContracts.getSelectedIndex());
          dialog = new ContractDialog(this, model);
          dialog.setVisible(true);
          if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
          else if (dialog.isModified()) {
            this.recentError = this.transactioner.updateContract(model);
            populate(this.listContracts, this.modeller.getContractList());
          }
        }
        break;
      case INSERT:
        model = new Contract();
        dialog = new ContractDialog(this, model);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified())
          this.recentError = this.transactioner.insertContract(model);
        break;
      case DELETE:
        this.recentError = isBlank(this.listContracts) ? Errors.LISTSELECT
            : this.transactioner.deleteContract(
                this.modeller.getContractModel(
                    this.listContracts.getSelectedIndex()));
        break;
    }
  }

  private void doGeneralManager(int verb) {
    GeneralManager model;
    GeneralManagerDialog dialog;
    switch (verb) {
      case GET:
        dialog = new GeneralManagerDialog(this, null);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified()) {
          this.recentError
              = this.transactioner.getGeneralManager(dialog.getModel());
          populate(
              this.listGeneralManagers, this.modeller.getGeneralManagerList());
        }
        break;
      case LIST:
        this.recentError = this.transactioner.listGeneralManagers();
        populate(
            this.listGeneralManagers, this.modeller.getGeneralManagerList());
        break;
      case VIEW:
        if (isBlank(this.listGeneralManagers))
          this.recentError = Errors.LISTSELECT;
        else {
          model = this.modeller.getGeneralManagerModel(
              this.listGeneralManagers.getSelectedIndex());
          dialog = new GeneralManagerDialog(this, model);
          dialog.setVisible(true);
          if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
          else if (dialog.isModified()) {
            this.recentError = this.transactioner.updateGeneralManager(model);
            populate(
                this.listGeneralManagers, this.modeller.getGeneralManagerList());
          }
        }
        break;
      case INSERT:
        model = new GeneralManager();
        dialog = new GeneralManagerDialog(this, model);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified())
          this.recentError = this.transactioner.insertGeneralManager(model);
        break;
      case DELETE:
        this.recentError = isBlank(this.listGeneralManagers) ? Errors.LISTSELECT
            : this.transactioner.deleteGeneralManager(
                this.modeller.getGeneralManagerModel(
                    this.listGeneralManagers.getSelectedIndex()));
        break;
    }
  }

  private void doLeague(int verb) {
    League model;
    LeagueDialog dialog;
    switch (verb) {
      case GET:
        dialog = new LeagueDialog(this, null);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified()) {
          this.recentError = this.transactioner.getLeague(dialog.getModel());
          populate(this.listLeagues, this.modeller.getLeagueList());
        }
        break;
      case LIST:
        this.recentError = this.transactioner.listLeagues();
        populate(this.listLeagues, this.modeller.getLeagueList());
        break;
      case VIEW:
        if (isBlank(this.listLeagues)) this.recentError = Errors.LISTSELECT;
        else {
          model = this.modeller
              .getLeagueModel(this.listLeagues.getSelectedIndex());
          dialog = new LeagueDialog(this, model);
          dialog.setVisible(true);
          if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
          else if (dialog.isModified()) {
            this.recentError = this.transactioner.updateLeague(model);
            populate(this.listLeagues, this.modeller.getLeagueList());
          }
        }
        break;
      case INSERT:
        model = new League();
        dialog = new LeagueDialog(this, model);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified())
          this.recentError = this.transactioner.insertLeague(model);
        break;
      case DELETE:
        this.recentError = isBlank(this.listLeagues) ? Errors.LISTSELECT
            : this.transactioner.deleteLeague(
                this.modeller.getLeagueModel(
                    this.listLeagues.getSelectedIndex()));
        break;
    }
  }

  private void doPlayer(int verb) {
    Player model;
    PlayerDialog dialog;
    switch (verb) {
      case GET:
        dialog = new PlayerDialog(this, null);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified()) {
          this.recentError = this.transactioner.getPlayer(dialog.getModel());
          populate(this.listPlayers, this.modeller.getPlayerList());
        }
        break;
      case LIST:
        this.recentError = this.transactioner.listPlayers();
        populate(this.listPlayers, this.modeller.getPlayerList());
        break;
      case VIEW:
        if (isBlank(this.listPlayers)) this.recentError = Errors.LISTSELECT;
        else {
          model = this.modeller
              .getPlayerModel(this.listPlayers.getSelectedIndex());
          dialog = new PlayerDialog(this, model);
          dialog.setVisible(true);
          if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
          else if (dialog.isModified()) {
            this.recentError = this.transactioner.updatePlayer(model);
            populate(this.listPlayers, this.modeller.getPlayerList());
          }
        }
        break;
      case INSERT:
        model = new Player();
        dialog = new PlayerDialog(this, model);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified())
          this.recentError = this.transactioner.insertPlayer(model);
        break;
      case DELETE:
        this.recentError = isBlank(this.listPlayers) ? Errors.LISTSELECT
            : this.transactioner.deletePlayer(
                this.modeller.getPlayerModel(
                    this.listPlayers.getSelectedIndex()));
        break;
    }
  }

  private void doPlays(int verb) {
    Plays model;
    PlaysDialog dialog;
    switch (verb) {
      case GET:
        dialog = new PlaysDialog(this, null);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified()) {
          this.recentError = this.transactioner.getPlays(dialog.getModel());
          populate(this.listPlays, this.modeller.getPlaysList());
        }
        break;
      case LIST:
        this.recentError = this.transactioner.listPlays();
        populate(this.listPlays, this.modeller.getPlaysList());
        break;
      case VIEW:
        if (isBlank(this.listPlays)) this.recentError = Errors.LISTSELECT;
        else {
          model = this.modeller
              .getPlaysModel(this.listPlays.getSelectedIndex());
          dialog = new PlaysDialog(this, model);
          dialog.setVisible(true);
          if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
          else if (dialog.isModified()) {
            this.recentError = this.transactioner.updatePlays(model);
            populate(this.listPlays, this.modeller.getPlaysList());
          }
        }
        break;
      case INSERT:
        model = new Plays();
        dialog = new PlaysDialog(this, model);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified())
          this.recentError = this.transactioner.insertPlays(model);
        break;
      case DELETE:
        this.recentError = isBlank(this.listPlays) ? Errors.LISTSELECT
            : this.transactioner.deletePlays(
                this.modeller.getPlaysModel(
                    this.listPlays.getSelectedIndex()));
        break;
    }
  }

  private void doTeam(int verb) {
    Team model;
    TeamDialog dialog;
    switch (verb) {
      case GET:
        dialog = new TeamDialog(this, null);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified()) {
          this.recentError = this.transactioner.getTeam(dialog.getModel());
          populate(this.listTeams, this.modeller.getTeamList());
        }
        break;
      case LIST:
        this.recentError = this.transactioner.listTeams();
        populate(this.listTeams, this.modeller.getTeamList());
        break;
      case VIEW:
        if (isBlank(this.listTeams)) this.recentError = Errors.LISTSELECT;
        else {
          model = this.modeller
              .getTeamModel(this.listTeams.getSelectedIndex());
          dialog = new TeamDialog(this, model);
          dialog.setVisible(true);
          if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
          else if (dialog.isModified()) {
            this.recentError = this.transactioner.updateTeam(model);
            populate(this.listTeams, this.modeller.getTeamList());
          }
        }
        break;
      case INSERT:
        model = new Team();
        dialog = new TeamDialog(this, model);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified())
          this.recentError = this.transactioner.insertTeam(model);
        break;
      case DELETE:
        this.recentError = isBlank(this.listTeams) ? Errors.LISTSELECT
            : this.transactioner.deleteTeam(
                this.modeller.getTeamModel(
                    this.listTeams.getSelectedIndex()));
        break;
    }
  }

  private void doTrade(int verb) {
    Trade model;
    TradeDialog dialog;
    switch (verb) {
      case GET:
        dialog = new TradeDialog(this, null);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified()) {
          this.recentError = this.transactioner.getTrade(dialog.getModel());
          populate(this.listTrades, this.modeller.getTradeList());
        }
        break;
      case LIST:
        this.recentError = this.transactioner.listTrades();
        populate(this.listTrades, this.modeller.getTradeList());
        break;
      case VIEW:
        if (isBlank(this.listTrades)) this.recentError = Errors.LISTSELECT;
        else {
          model = this.modeller
              .getTradeModel(this.listTrades.getSelectedIndex());
          dialog = new TradeDialog(this, model);
          dialog.setVisible(true);
          if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
          else if (dialog.isModified()) {
            this.recentError = this.transactioner.updateTrade(model);
            populate(this.listTrades, this.modeller.getTradeList());
          }
        }
        break;
      case INSERT:
        model = new Trade();
        dialog = new TradeDialog(this, model);
        dialog.setVisible(true);
        if (dialog.isCancelled()) this.recentError = Errors.CANCEL;
        else if (dialog.isModified())
          this.recentError = this.transactioner.insertTrade(model);
        break;
      case DELETE:
        this.recentError = isBlank(this.listTrades) ? Errors.LISTSELECT
            : this.transactioner.deleteTrade(
                this.modeller.getTradeModel(
                    this.listTrades.getSelectedIndex()));
        break;
    }
  }

  private void doMagic(int verb) {
    String term;
    switch (verb) {
      case SELECT:
        term = JOptionPane.showInputDialog(this,
            "Search Term: ", "Filter Players", JOptionPane.PLAIN_MESSAGE);
        if (term == null) {
          this.recentError = Errors.CANCEL;
          return;
        }
        this.recentError = this.magician.doSelect(term);
        break;
      case PROJECT:
        this.recentError = this.magician.doProject();
        break;
      case JOIN:
        term = JOptionPane.showInputDialog(this,
            "Search Term: ", "Filter Plays", JOptionPane.PLAIN_MESSAGE);
        if (term == null) {
          this.recentError = Errors.CANCEL;
          return;
        }
        this.recentError = this.magician.doJoin(term);
        break;
      case GROUP:
        this.recentError = this.magician.doGroup();
        break;
      case HAVING:
        this.recentError = this.magician.doHaving();
        break;
      case NESTED:
        this.recentError = this.magician.doNested();
        break;
      case DIVIDE:
        this.recentError = this.magician.doDivide();
        break;
    }
    populate(this.listMagics, this.modeller.getMagics());
  }

  private void populate(JList list, Object[] objects) {
    list.setListData(objects);
  }

  private boolean isBlank(JList list) {
    return list.getModel() == null || list.isSelectionEmpty();
  }

  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    labelActions = new javax.swing.JLabel();
    buttonGet = new javax.swing.JButton();
    buttonList = new javax.swing.JButton();
    buttonView = new javax.swing.JButton();
    buttonInsert = new javax.swing.JButton();
    buttonDelete = new javax.swing.JButton();
    paneMain = new javax.swing.JTabbedPane();
    panelAgents = new javax.swing.JPanel();
    paneAgents = new javax.swing.JScrollPane();
    listAgents = new javax.swing.JList<>();
    panelCoaches = new javax.swing.JPanel();
    paneCoaches = new javax.swing.JScrollPane();
    listCoaches = new javax.swing.JList<>();
    panelContracts = new javax.swing.JPanel();
    paneContracts = new javax.swing.JScrollPane();
    listContracts = new javax.swing.JList<>();
    panelGeneralManagers = new javax.swing.JPanel();
    paneGeneralManagers = new javax.swing.JScrollPane();
    listGeneralManagers = new javax.swing.JList<>();
    panelLeagues = new javax.swing.JPanel();
    paneLeagues = new javax.swing.JScrollPane();
    listLeagues = new javax.swing.JList<>();
    panelPlayers = new javax.swing.JPanel();
    panePlayers = new javax.swing.JScrollPane();
    listPlayers = new javax.swing.JList<>();
    panelPlays = new javax.swing.JPanel();
    panePlays = new javax.swing.JScrollPane();
    listPlays = new javax.swing.JList<>();
    panelTeams = new javax.swing.JPanel();
    paneTeams = new javax.swing.JScrollPane();
    listTeams = new javax.swing.JList<>();
    panelTrades = new javax.swing.JPanel();
    paneTrades = new javax.swing.JScrollPane();
    listTrades = new javax.swing.JList<>();
    panelMagics = new javax.swing.JPanel();
    paneMagics = new javax.swing.JScrollPane();
    listMagics = new javax.swing.JList<>();
    labelStatus = new javax.swing.JLabel();
    menus = new javax.swing.JMenuBar();
    menuApplication = new javax.swing.JMenu();
    menuAbout = new javax.swing.JMenuItem();
    menuQuit = new javax.swing.JMenuItem();
    menuMagic = new javax.swing.JMenu();
    menuResetDatabase = new javax.swing.JMenuItem();
    menuSelect = new javax.swing.JMenuItem();
    menuProject = new javax.swing.JMenuItem();
    menuJoin = new javax.swing.JMenuItem();
    menuGroup = new javax.swing.JMenuItem();
    menuHaving = new javax.swing.JMenuItem();
    menuNested = new javax.swing.JMenuItem();
    menuDivide = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("RDB");
    setMinimumSize(new java.awt.Dimension(480, 360));
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    labelActions.setText("Actions:");

    buttonGet.setText("Get");
    buttonGet.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonGetActionPerformed(evt);
      }
    });

    buttonList.setText("List");
    buttonList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonListActionPerformed(evt);
      }
    });

    buttonView.setText("View / Edit...");
    buttonView.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonViewActionPerformed(evt);
      }
    });

    buttonInsert.setText("Insert...");
    buttonInsert.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonInsertActionPerformed(evt);
      }
    });

    buttonDelete.setText("Delete");
    buttonDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonDeleteActionPerformed(evt);
      }
    });

    paneAgents.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    listAgents.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    paneAgents.setViewportView(listAgents);

    javax.swing.GroupLayout panelAgentsLayout = new javax.swing.GroupLayout(panelAgents);
    panelAgents.setLayout(panelAgentsLayout);
    panelAgentsLayout.setHorizontalGroup(
      panelAgentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneAgents)
    );
    panelAgentsLayout.setVerticalGroup(
      panelAgentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneAgents, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
    );

    paneMain.addTab("Agents", panelAgents);

    paneCoaches.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    listCoaches.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    paneCoaches.setViewportView(listCoaches);

    javax.swing.GroupLayout panelCoachesLayout = new javax.swing.GroupLayout(panelCoaches);
    panelCoaches.setLayout(panelCoachesLayout);
    panelCoachesLayout.setHorizontalGroup(
      panelCoachesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneCoaches)
    );
    panelCoachesLayout.setVerticalGroup(
      panelCoachesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneCoaches, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
    );

    paneMain.addTab("Coaches", panelCoaches);

    paneContracts.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    listContracts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    paneContracts.setViewportView(listContracts);

    javax.swing.GroupLayout panelContractsLayout = new javax.swing.GroupLayout(panelContracts);
    panelContracts.setLayout(panelContractsLayout);
    panelContractsLayout.setHorizontalGroup(
      panelContractsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneContracts)
    );
    panelContractsLayout.setVerticalGroup(
      panelContractsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneContracts, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
    );

    paneMain.addTab("Contracts", panelContracts);

    paneGeneralManagers.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    listGeneralManagers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    paneGeneralManagers.setViewportView(listGeneralManagers);

    javax.swing.GroupLayout panelGeneralManagersLayout = new javax.swing.GroupLayout(panelGeneralManagers);
    panelGeneralManagers.setLayout(panelGeneralManagersLayout);
    panelGeneralManagersLayout.setHorizontalGroup(
      panelGeneralManagersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneGeneralManagers)
    );
    panelGeneralManagersLayout.setVerticalGroup(
      panelGeneralManagersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneGeneralManagers, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
    );

    paneMain.addTab("General Managers", panelGeneralManagers);

    paneLeagues.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    listLeagues.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    paneLeagues.setViewportView(listLeagues);

    javax.swing.GroupLayout panelLeaguesLayout = new javax.swing.GroupLayout(panelLeagues);
    panelLeagues.setLayout(panelLeaguesLayout);
    panelLeaguesLayout.setHorizontalGroup(
      panelLeaguesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneLeagues)
    );
    panelLeaguesLayout.setVerticalGroup(
      panelLeaguesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneLeagues, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
    );

    paneMain.addTab("Leagues", panelLeagues);

    panePlayers.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    listPlayers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    panePlayers.setViewportView(listPlayers);

    javax.swing.GroupLayout panelPlayersLayout = new javax.swing.GroupLayout(panelPlayers);
    panelPlayers.setLayout(panelPlayersLayout);
    panelPlayersLayout.setHorizontalGroup(
      panelPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(panePlayers)
    );
    panelPlayersLayout.setVerticalGroup(
      panelPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(panePlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
    );

    paneMain.addTab("Players", panelPlayers);

    panePlays.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    listPlays.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    panePlays.setViewportView(listPlays);

    javax.swing.GroupLayout panelPlaysLayout = new javax.swing.GroupLayout(panelPlays);
    panelPlays.setLayout(panelPlaysLayout);
    panelPlaysLayout.setHorizontalGroup(
      panelPlaysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(panePlays)
    );
    panelPlaysLayout.setVerticalGroup(
      panelPlaysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(panePlays, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
    );

    paneMain.addTab("Plays", panelPlays);

    paneTeams.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    listTeams.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    paneTeams.setViewportView(listTeams);

    javax.swing.GroupLayout panelTeamsLayout = new javax.swing.GroupLayout(panelTeams);
    panelTeams.setLayout(panelTeamsLayout);
    panelTeamsLayout.setHorizontalGroup(
      panelTeamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneTeams)
    );
    panelTeamsLayout.setVerticalGroup(
      panelTeamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneTeams, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
    );

    paneMain.addTab("Teams", panelTeams);

    paneTrades.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    listTrades.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    paneTrades.setViewportView(listTrades);

    javax.swing.GroupLayout panelTradesLayout = new javax.swing.GroupLayout(panelTrades);
    panelTrades.setLayout(panelTradesLayout);
    panelTradesLayout.setHorizontalGroup(
      panelTradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneTrades)
    );
    panelTradesLayout.setVerticalGroup(
      panelTradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneTrades, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
    );

    paneMain.addTab("Trades", panelTrades);

    paneMagics.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    listMagics.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    paneMagics.setViewportView(listMagics);

    javax.swing.GroupLayout panelMagicsLayout = new javax.swing.GroupLayout(panelMagics);
    panelMagics.setLayout(panelMagicsLayout);
    panelMagicsLayout.setHorizontalGroup(
      panelMagicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneMagics)
    );
    panelMagicsLayout.setVerticalGroup(
      panelMagicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(paneMagics, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
    );

    paneMain.addTab("Magics", panelMagics);

    labelStatus.setText("Ready.");

    menuApplication.setText("Application");

    menuAbout.setText("About");
    menuAbout.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuAboutActionPerformed(evt);
      }
    });
    menuApplication.add(menuAbout);

    menuQuit.setText("Quit");
    menuQuit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuQuitActionPerformed(evt);
      }
    });
    menuApplication.add(menuQuit);

    menus.add(menuApplication);

    menuMagic.setText("Magic");

    menuResetDatabase.setText("Reset Database...");
    menuResetDatabase.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuResetDatabaseActionPerformed(evt);
      }
    });
    menuMagic.add(menuResetDatabase);

    menuSelect.setText("Filter Players...");
    menuSelect.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuSelectActionPerformed(evt);
      }
    });
    menuMagic.add(menuSelect);

    menuProject.setText("List Players");
    menuProject.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuProjectActionPerformed(evt);
      }
    });
    menuMagic.add(menuProject);

    menuJoin.setText("Filter Plays...");
    menuJoin.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuJoinActionPerformed(evt);
      }
    });
    menuMagic.add(menuJoin);

    menuGroup.setText("List Coaches");
    menuGroup.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuGroupActionPerformed(evt);
      }
    });
    menuMagic.add(menuGroup);

    menuHaving.setText("Get Youngest Players");
    menuHaving.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuHavingActionPerformed(evt);
      }
    });
    menuMagic.add(menuHaving);

    menuNested.setText("Get Average Payrolls");
    menuNested.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuNestedActionPerformed(evt);
      }
    });
    menuMagic.add(menuNested);

    menuDivide.setText("Get All-Stars");
    menuDivide.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuDivideActionPerformed(evt);
      }
    });
    menuMagic.add(menuDivide);

    menus.add(menuMagic);

    setJMenuBar(menus);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(labelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(paneMain)
              .addGroup(layout.createSequentialGroup()
                .addComponent(labelActions)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonGet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonView)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonInsert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonDelete)
                .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(buttonGet)
          .addComponent(buttonList)
          .addComponent(buttonInsert)
          .addComponent(labelActions)
          .addComponent(buttonView)
          .addComponent(buttonDelete))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(paneMain, javax.swing.GroupLayout.PREFERRED_SIZE, 170, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void buttonGetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGetActionPerformed
    doCommand(GET);
  }//GEN-LAST:event_buttonGetActionPerformed

  private void buttonListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonListActionPerformed
    doCommand(LIST);
  }//GEN-LAST:event_buttonListActionPerformed

  private void buttonViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonViewActionPerformed
    doCommand(VIEW);
  }//GEN-LAST:event_buttonViewActionPerformed

  private void buttonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInsertActionPerformed
    doCommand(INSERT);
  }//GEN-LAST:event_buttonInsertActionPerformed

  private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
    doCommand(DELETE);
  }//GEN-LAST:event_buttonDeleteActionPerformed

  private void menuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAboutActionPerformed
    AuthFrame.showAboutDialog(this);
  }//GEN-LAST:event_menuAboutActionPerformed

  private void menuQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuQuitActionPerformed
    formWindowClosing(null);
  }//GEN-LAST:event_menuQuitActionPerformed

  private void menuResetDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuResetDatabaseActionPerformed
    if (JOptionPane.showConfirmDialog(this,
        "Purge and populate with MockKit models?", "Reset Database",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
        == JOptionPane.YES_OPTION) {
      this.labelStatus.setText("Resetting database...");
      update(getGraphics());
      this.recentError = this.transactioner.resetDatabase();
      this.labelStatus.setText(Errors.parseErrorCode(this.recentError));
    }
  }//GEN-LAST:event_menuResetDatabaseActionPerformed

  private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    labelStatus.setText("Closing database...");
    update(getGraphics());
    this.recentError = this.transactioner.closeDatabase();
    if (this.recentError != Errors.SUCCESS)
      JOptionPane.showMessageDialog(this, "The database failed to close.\n\n"
          + Errors.parseErrorCode(this.recentError),
          "RDB", JOptionPane.ERROR_MESSAGE);
    dispose();
  }//GEN-LAST:event_formWindowClosing

  private void menuSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSelectActionPerformed
    doCommand(SELECT);
  }//GEN-LAST:event_menuSelectActionPerformed

  private void menuProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProjectActionPerformed
    doCommand(PROJECT);
  }//GEN-LAST:event_menuProjectActionPerformed

  private void menuJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuJoinActionPerformed
    doCommand(JOIN);
  }//GEN-LAST:event_menuJoinActionPerformed

  private void menuGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGroupActionPerformed
    doCommand(GROUP);
  }//GEN-LAST:event_menuGroupActionPerformed

  private void menuHavingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHavingActionPerformed
    doCommand(HAVING);
  }//GEN-LAST:event_menuHavingActionPerformed

  private void menuNestedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNestedActionPerformed
    doCommand(NESTED);
  }//GEN-LAST:event_menuNestedActionPerformed

  private void menuDivideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDivideActionPerformed
    doCommand(DIVIDE);
  }//GEN-LAST:event_menuDivideActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonDelete;
  private javax.swing.JButton buttonGet;
  private javax.swing.JButton buttonInsert;
  private javax.swing.JButton buttonList;
  private javax.swing.JButton buttonView;
  private javax.swing.JLabel labelActions;
  private javax.swing.JLabel labelStatus;
  private javax.swing.JList<String> listAgents;
  private javax.swing.JList<String> listCoaches;
  private javax.swing.JList<String> listContracts;
  private javax.swing.JList<String> listGeneralManagers;
  private javax.swing.JList<String> listLeagues;
  private javax.swing.JList<String> listMagics;
  private javax.swing.JList<String> listPlayers;
  private javax.swing.JList<String> listPlays;
  private javax.swing.JList<String> listTeams;
  private javax.swing.JList<String> listTrades;
  private javax.swing.JMenuItem menuAbout;
  private javax.swing.JMenu menuApplication;
  private javax.swing.JMenuItem menuDivide;
  private javax.swing.JMenuItem menuGroup;
  private javax.swing.JMenuItem menuHaving;
  private javax.swing.JMenuItem menuJoin;
  private javax.swing.JMenu menuMagic;
  private javax.swing.JMenuItem menuNested;
  private javax.swing.JMenuItem menuProject;
  private javax.swing.JMenuItem menuQuit;
  private javax.swing.JMenuItem menuResetDatabase;
  private javax.swing.JMenuItem menuSelect;
  private javax.swing.JMenuBar menus;
  private javax.swing.JScrollPane paneAgents;
  private javax.swing.JScrollPane paneCoaches;
  private javax.swing.JScrollPane paneContracts;
  private javax.swing.JScrollPane paneGeneralManagers;
  private javax.swing.JScrollPane paneLeagues;
  private javax.swing.JScrollPane paneMagics;
  private javax.swing.JTabbedPane paneMain;
  private javax.swing.JScrollPane panePlayers;
  private javax.swing.JScrollPane panePlays;
  private javax.swing.JScrollPane paneTeams;
  private javax.swing.JScrollPane paneTrades;
  private javax.swing.JPanel panelAgents;
  private javax.swing.JPanel panelCoaches;
  private javax.swing.JPanel panelContracts;
  private javax.swing.JPanel panelGeneralManagers;
  private javax.swing.JPanel panelLeagues;
  private javax.swing.JPanel panelMagics;
  private javax.swing.JPanel panelPlayers;
  private javax.swing.JPanel panelPlays;
  private javax.swing.JPanel panelTeams;
  private javax.swing.JPanel panelTrades;
  // End of variables declaration//GEN-END:variables
}
