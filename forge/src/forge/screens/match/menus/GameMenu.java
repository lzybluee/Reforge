package forge.screens.match.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import forge.assets.FSkinProp;
import forge.menus.MenuUtil;
import forge.model.FModel;
import forge.properties.ForgePreferences;
import forge.properties.ForgePreferences.FPref;
import forge.screens.match.CMatchUI;
import forge.screens.match.VAutoYields;
import forge.screens.match.controllers.CDock.ArcState;
import forge.screens.match.controllers.CDock.StartPlayerOption;
import forge.toolbox.FSkin.SkinIcon;
import forge.toolbox.FSkin.SkinnedCheckBoxMenuItem;
import forge.toolbox.FSkin.SkinnedMenu;
import forge.toolbox.FSkin.SkinnedMenuItem;
import forge.toolbox.FSkin.SkinnedRadioButtonMenuItem;

/**
 * Returns a JMenu containing options associated with current game.
 * <p>
 * Replicates options available in Dock tab.
 */
public final class GameMenu {
    private final CMatchUI matchUI;
    public GameMenu(final CMatchUI matchUI) {
        this.matchUI = matchUI;
    }

    private static ForgePreferences prefs = FModel.getPreferences();
    private static boolean showIcons;

    public JMenu getMenu() {
    	matchUI.getCDock().loadPreferences();
        final JMenu menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);
        menu.add(getMenuItem_Undo());
        menu.add(getMenuItem_Concede());
        menu.add(getMenuItem_EndTurn());
        menu.add(getMenuItem_AlphaStrike());
        menu.addSeparator();
        menu.add(getMenuItem_TargetingArcs());
        menu.add(new CardOverlaysMenu(matchUI).getMenu());
        menu.add(getMenuItem_AutoYields());
        menu.add(getMenuItem_SimpleStack());
        menu.add(getMenuItem_PreventBeforeReplace());
        menu.add(getMenuItem_SkipRestoreDeck());
        menu.add(getMenuItem_StartPlayer());
        menu.addSeparator();
        menu.add(getMenuItem_ViewDeckList());
        menu.add(getMenuItem_ViewOpponentDeckList());
        menu.addSeparator();
        menu.add(getMenuItem_GameSoundEffects());
        return menu;
    }

    private static SkinnedCheckBoxMenuItem getMenuItem_SimpleStack() {
        SkinnedCheckBoxMenuItem menuItem = new SkinnedCheckBoxMenuItem("Simple Stack");
        menuItem.setState(prefs.getPrefBoolean(FPref.UI_SIMPLE_STACK));
        menuItem.addActionListener(getSimpleStackAction());
        return menuItem;
    }
    private static ActionListener getSimpleStackAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                toggleSimpleStack();
            }
        };
    }
    private static void toggleSimpleStack() {
        final boolean isSimpleStack = !prefs.getPrefBoolean(FPref.UI_SIMPLE_STACK);
        prefs.setPref(FPref.UI_SIMPLE_STACK, isSimpleStack);
        prefs.save();
    }
    
    private static SkinnedCheckBoxMenuItem getMenuItem_PreventBeforeReplace() {
        SkinnedCheckBoxMenuItem menuItem = new SkinnedCheckBoxMenuItem("Prevent Before Replace");
        menuItem.setState(prefs.getPrefBoolean(FPref.UI_PREVENT_BEFORE_REPLACE));
        menuItem.addActionListener(getPreventBeforeReplaceAction());
        return menuItem;
    }
    private static ActionListener getPreventBeforeReplaceAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
            	togglePreventBeforeReplace();
            }
        };
    }
    private static void togglePreventBeforeReplace() {
        final boolean preventBeforeReplace = !prefs.getPrefBoolean(FPref.UI_PREVENT_BEFORE_REPLACE);
        prefs.setPref(FPref.UI_PREVENT_BEFORE_REPLACE, preventBeforeReplace);
        prefs.save();
    }
    
    private static SkinnedCheckBoxMenuItem getMenuItem_SkipRestoreDeck() {
        SkinnedCheckBoxMenuItem menuItem = new SkinnedCheckBoxMenuItem("Keep Sideboard");
        menuItem.setState(prefs.getPrefBoolean(FPref.UI_SKIP_RESTORE_DECK));
        menuItem.addActionListener(getSkipRestoreDeck());
        return menuItem;
    }
    private static ActionListener getSkipRestoreDeck() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
            	toggleSkipRestoreDeck();
            }
        };
    }
    private static void toggleSkipRestoreDeck() {
        final boolean skipRestoreDeck = !prefs.getPrefBoolean(FPref.UI_SKIP_RESTORE_DECK);
        prefs.setPref(FPref.UI_SKIP_RESTORE_DECK, skipRestoreDeck);
        prefs.save();
    }
    
    private static SkinnedCheckBoxMenuItem getMenuItem_GameSoundEffects() {
        SkinnedCheckBoxMenuItem menuItem = new SkinnedCheckBoxMenuItem("Sound Effects");
        menuItem.setState(prefs.getPrefBoolean(FPref.UI_ENABLE_SOUNDS));
        menuItem.addActionListener(getSoundEffectsAction());
        return menuItem;
    }
    private static ActionListener getSoundEffectsAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                toggleGameSoundEffects();
            }
        };
    }
    private static void toggleGameSoundEffects() {
        final boolean isSoundEffectsEnabled = !prefs.getPrefBoolean(FPref.UI_ENABLE_SOUNDS);
        prefs.setPref(FPref.UI_ENABLE_SOUNDS, isSoundEffectsEnabled);
        prefs.save();
    }

    private SkinnedMenuItem getMenuItem_Undo() {
        final SkinnedMenuItem menuItem = new SkinnedMenuItem("Undo");
        menuItem.setAccelerator(MenuUtil.getAcceleratorKey(KeyEvent.VK_Z));
        menuItem.addActionListener(getUndoAction());
        return menuItem;
    }

    private ActionListener getUndoAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                matchUI.getGameController().undoLastAction();
            }
        };
    }

    private SkinnedMenuItem getMenuItem_Concede() {
        SkinnedMenuItem menuItem = new SkinnedMenuItem(matchUI.getConcedeCaption());
        menuItem.setIcon((showIcons ? MenuUtil.getMenuIcon(FSkinProp.ICO_CONCEDE) : null));
        menuItem.setAccelerator(MenuUtil.getAcceleratorKey(KeyEvent.VK_Q));
        menuItem.addActionListener(getConcedeAction());
        return menuItem;
    }

    private ActionListener getConcedeAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                matchUI.concede();
            }
        };
    }

    private SkinnedMenuItem getMenuItem_AlphaStrike() {
        final SkinnedMenuItem menuItem = new SkinnedMenuItem("Alpha Strike");
        menuItem.setIcon((showIcons ? MenuUtil.getMenuIcon(FSkinProp.ICO_ALPHASTRIKE) : null));
        menuItem.setAccelerator(MenuUtil.getAcceleratorKey(KeyEvent.VK_A));
        menuItem.addActionListener(getAlphaStrikeAction());
        return menuItem;
    }

    private ActionListener getAlphaStrikeAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                matchUI.getGameController().alphaStrike();
            }
        };
    }

    private SkinnedMenuItem getMenuItem_EndTurn() {
        final SkinnedMenuItem menuItem = new SkinnedMenuItem("End Turn");
        menuItem.setIcon((showIcons ? MenuUtil.getMenuIcon(FSkinProp.ICO_ENDTURN) : null));
        menuItem.setAccelerator(MenuUtil.getAcceleratorKey(KeyEvent.VK_E));
        menuItem.addActionListener(getEndTurnAction());
        return menuItem;
    }

    private ActionListener getEndTurnAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                matchUI.getGameController().passPriorityUntilEndOfTurn();
            }
        };
    }

    private SkinnedMenu getMenuItem_TargetingArcs() {
        final SkinnedMenu menu = new SkinnedMenu("Targeting Arcs");
        final ButtonGroup group = new ButtonGroup();

        SkinIcon menuIcon = MenuUtil.getMenuIcon(FSkinProp.ICO_ARCSOFF);

        SkinnedRadioButtonMenuItem menuItem;
        menuItem = getTargetingArcRadioButton("Off", FSkinProp.ICO_ARCSOFF, ArcState.OFF);
        if (menuItem.isSelected()) { menuIcon = MenuUtil.getMenuIcon(FSkinProp.ICO_ARCSOFF); }
        group.add(menuItem);
        menu.add(menuItem);
        menuItem = getTargetingArcRadioButton("Card mouseover", FSkinProp.ICO_ARCSHOVER, ArcState.MOUSEOVER);
        if (menuItem.isSelected()) { menuIcon = MenuUtil.getMenuIcon(FSkinProp.ICO_ARCSHOVER); }
        group.add(menuItem);
        menu.add(menuItem);
        menuItem = getTargetingArcRadioButton("Always On", FSkinProp.ICO_ARCSON, ArcState.ON);
        if (menuItem.isSelected()) { menuIcon = MenuUtil.getMenuIcon(FSkinProp.ICO_ARCSON); }
        group.add(menuItem);

        menu.setIcon((showIcons ? menuIcon : null));
        menu.add(menuItem);

        return menu;
    }

    private SkinnedRadioButtonMenuItem getTargetingArcRadioButton(final String caption, final FSkinProp icon, final ArcState arcState) {
        final SkinnedRadioButtonMenuItem menuItem = new SkinnedRadioButtonMenuItem(caption);
        menuItem.setIcon((showIcons ? MenuUtil.getMenuIcon(icon) : null));
        menuItem.setSelected(arcState == matchUI.getCDock().getArcState());
        menuItem.addActionListener(getTargetingRadioButtonAction(arcState));
        return menuItem;
    }

    private ActionListener getTargetingRadioButtonAction(final ArcState arcState) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                prefs.setPref(FPref.UI_TARGETING_OVERLAY, String.valueOf(arcState.ordinal()));
                prefs.save();
                matchUI.getCDock().setArcState(arcState);
                setTargetingArcMenuIcon((SkinnedRadioButtonMenuItem)e.getSource());
            }
        };
    }
    
    private SkinnedMenu getMenuItem_StartPlayer() {
        final SkinnedMenu menu = new SkinnedMenu("Start Player");
        final ButtonGroup group = new ButtonGroup();

        SkinnedRadioButtonMenuItem menuItem;
        menuItem = getStartPlayerRadioButton("Random", StartPlayerOption.RANDOM);
        group.add(menuItem);
        menu.add(menuItem);
        menuItem = getStartPlayerRadioButton("Player", StartPlayerOption.PLAYER);
        group.add(menuItem);
        menu.add(menuItem);
        menuItem = getStartPlayerRadioButton("Opponent", StartPlayerOption.OPPONENT);
        group.add(menuItem);
        menu.add(menuItem);

        return menu;
    }
    
    private SkinnedRadioButtonMenuItem getStartPlayerRadioButton(final String caption, final StartPlayerOption option) {
        final SkinnedRadioButtonMenuItem menuItem = new SkinnedRadioButtonMenuItem(caption);
        menuItem.setSelected(option == matchUI.getCDock().getStartPlayerOption());
        menuItem.addActionListener(getStartPlayerButtonAction(option));
        return menuItem;
    }
    
    private ActionListener getStartPlayerButtonAction(final StartPlayerOption option) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                prefs.setPref(FPref.UI_START_PLAYER, String.valueOf(option.ordinal()));
                prefs.save();
                matchUI.getCDock().setStartPlayerOption(option);
            }
        };
    }

    private static void setTargetingArcMenuIcon(SkinnedRadioButtonMenuItem item) {
        final JPopupMenu pop = (JPopupMenu)item.getParent();
        final JMenu menu = (JMenu)pop.getInvoker();
        menu.setIcon(item.getIcon());
    }

    private SkinnedMenuItem getMenuItem_AutoYields() {
        final SkinnedMenuItem menuItem = new SkinnedMenuItem("Auto-Yields");
        menuItem.setIcon((showIcons ? MenuUtil.getMenuIcon(FSkinProp.ICO_WARNING) : null));
        menuItem.addActionListener(getAutoYieldsAction());
        return menuItem;
    }

    private ActionListener getAutoYieldsAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final VAutoYields autoYields = new VAutoYields(matchUI);
                autoYields.showAutoYields();
            }
        };
    }

    private SkinnedMenuItem getMenuItem_ViewDeckList() {
        final SkinnedMenuItem menuItem = new SkinnedMenuItem("Deck List");
        menuItem.setIcon((showIcons ? MenuUtil.getMenuIcon(FSkinProp.ICO_DECKLIST) : null));
        menuItem.addActionListener(getViewDeckListAction());
        return menuItem;
    }
    
    private SkinnedMenuItem getMenuItem_ViewOpponentDeckList() {
        final SkinnedMenuItem menuItem = new SkinnedMenuItem("Opponent Deck List");
        menuItem.setIcon((showIcons ? MenuUtil.getMenuIcon(FSkinProp.ICO_DECKLIST) : null));
        menuItem.addActionListener(getViewOpponentDeckListAction());
        return menuItem;
    }

    private ActionListener getViewDeckListAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                matchUI.viewDeckList();
            }
        };
    }
    
    private ActionListener getViewOpponentDeckListAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                matchUI.viewOpponentDeckList();
            }
        };
    }
}
