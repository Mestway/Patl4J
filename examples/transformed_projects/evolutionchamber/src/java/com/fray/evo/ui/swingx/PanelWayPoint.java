package com.fray.evo.ui.swingx;

import com.fray.evo.EcState;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.ZergUnitLibrary;
import com.fray.evo.util.ZergUpgradeLibrary;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;


public class PanelWayPoint extends PanelBase
{
	private static final long serialVersionUID = -3838081126063166169L;
	private final EcState state;

	public PanelWayPoint(final TabFolder parent, final EcSwingX swingX, final EcState state)
	{
        super(parent, swingX, SWT.NONE, new FormLayout());

        this.state = state;
        swingX.gridx = 0;
        swingX.margintop = 5;
        
        swingX.addInput(this, messages.getString("waypoint.drones"), NumberTextField.class, new CustomActionListener()
        {

            @Override
            void reverse(Object o)
            {
            	Text c = (Text) o;
            	c.setText(Integer.toString(state.getDrones()));
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits(ZergUnitLibrary.Drone, EcSwingX.getDigit(e));
			}
        });
        swingX.addInput(this, messages.getString("waypoint.deadline"), TimeTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
            	c.setText(formatAsTime(state.targetSeconds));
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				state.targetSeconds = EcSwingX.getDigit(e);
				((Text)e.getSource()).setText(formatAsTime(state.targetSeconds));
			}
        }).setText(formatAsTime(state.targetSeconds));
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.overlords"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getOverlords()));
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits( ZergUnitLibrary.Overlord, EcSwingX.getDigit(e));
				
			}
        });
        swingX.addInput(this, messages.getString("waypoint.overseers"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getOverlords()));
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits(ZergUnitLibrary.Overseer, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        if (state == swingX.destination.get(swingX.destination.size()-1)) // only put this option on the Final waypoint.
        {
            swingX.addInput(this, messages.getString("waypoint.scoutTiming"), TimeTextField.class, new CustomActionListener()
            {
                void reverse(Object o)
                {
                	Text c = (Text) o;
                	c.setText(String.valueOf((swingX.destination.get(swingX.destination.size()-1).scoutDrone)));
                }
				@Override 
				public void widgetSelected(SelectionEvent e) {
					swingX.destination.get(swingX.destination.size()-1).scoutDrone = EcSwingX.getDigit(e);
					((Text)e.getSource()).setText( formatAsTime(state.scoutDrone) );
				}
            }).setText( formatAsTime(state.scoutDrone));
        }
        addCheck(messages.getString("waypoint.burrow"), new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isBurrow());
                
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
                if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Burrow);
                else state.removeUpgrade(ZergUpgradeLibrary.Burrow);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.queens"), NumberTextField.class, new CustomActionListener() {
            
            void reverse(Object o) {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getQueens()));
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits(ZergUnitLibrary.Queen, EcSwingX.getDigit(e));
				
			}
        });
        addCheck(messages.getString("waypoint.pneumatizedCarapace"), new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
                c.setSelection(state.isPneumatizedCarapace());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.PneumatizedCarapace);
                else state.removeUpgrade(ZergUpgradeLibrary.PneumatizedCarapace);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.zerglings"), NumberTextField.class, new CustomActionListener() {

            void reverse(Object o) {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getZerglings()));
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits(ZergUnitLibrary.Zergling, EcSwingX.getDigit(e));
			}
        });
        addCheck(messages.getString("waypoint.ventralSacs"), new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
                c.setSelection(state.isVentralSacs());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.VentralSacs);
                else state.removeUpgrade(ZergUpgradeLibrary.VentralSacs);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        addCheck(messages.getString("waypoint.metabolicBoost"), new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
                c.setSelection(state.isMetabolicBoost());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.MetabolicBoost);
                else state.removeUpgrade(ZergUpgradeLibrary.MetabolicBoost);
			}
        });
        addCheck(messages.getString("waypoint.adrenalGlands"), new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
                c.setSelection(state.isAdrenalGlands());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.AdrenalGlands);
                else state.removeUpgrade(ZergUpgradeLibrary.AdrenalGlands);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.banelings"), NumberTextField.class, new CustomActionListener() {

            void reverse(Object o) {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getBanelings()));
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits(ZergUnitLibrary.Baneling, EcSwingX.getDigit(e));
			}
        });
        addCheck(messages.getString("waypoint.centrifugalHooks"), new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
                c.setSelection(state.isCentrifugalHooks());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.CentrifugalHooks);
                else state.removeUpgrade(ZergUpgradeLibrary.CentrifugalHooks);
			}
        });

        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.roaches"), NumberTextField.class, new CustomActionListener() {
            
            void reverse(Object o) {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getRoaches()));
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits(ZergUnitLibrary.Roach, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        addCheck(messages.getString("waypoint.glialReconstitution"), new CustomActionListener() {
            void reverse(Object o) {
            	Button c = (Button) o;
                c.setSelection(state.isGlialReconstitution());
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.GlialReconstitution);
                else state.removeUpgrade(ZergUpgradeLibrary.GlialReconstitution);
			}
        });
        addCheck(messages.getString("waypoint.tunnelingClaws"), new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
                c.setSelection(state.isTunnelingClaws());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.TunnelingClaws);
                else state.removeUpgrade(ZergUpgradeLibrary.TunnelingClaws);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.hydralisks"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getHydralisks()));
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				 state.setUnits(ZergUnitLibrary.Hydralisk, EcSwingX.getDigit(e));
			}
        });
        addCheck(messages.getString("waypoint.groovedSpines"), new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
                c.setSelection(state.isGroovedSpines());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.GroovedSpines);
                else state.removeUpgrade(ZergUpgradeLibrary.GroovedSpines);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.infestors"), NumberTextField.class, new CustomActionListener() {
            
            void reverse(Object o) {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getInfestors()));
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits(ZergUnitLibrary.Infestor, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        addCheck(messages.getString("waypoint.neuralParasite"), new CustomActionListener() {
            
            void reverse(Object o) {
            	Button c = (Button) o;
                c.setSelection(state.isNeuralParasite());
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.NeuralParasite);
                else state.removeUpgrade(ZergUpgradeLibrary.NeuralParasite);
			}
        });
        addCheck(messages.getString("waypoint.pathogenGlands"), new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
                c.setSelection(state.isPathogenGlands());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.PathogenGlands);
                else state.removeUpgrade(ZergUpgradeLibrary.PathogenGlands);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.mutalisks"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getMutalisks()));
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits(ZergUnitLibrary.Mutalisk, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.ultralisks"), NumberTextField.class, new CustomActionListener() {
           
            void reverse(Object o) {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getUltralisks()));
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits(ZergUnitLibrary.Ultralisk, EcSwingX.getDigit(e));
				
			}
        });
        addCheck(messages.getString("waypoint.chitinousPlating"), new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
                c.setSelection(state.isChitinousPlating());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.ChitinousPlating);
                else state.removeUpgrade(ZergUpgradeLibrary.ChitinousPlating);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.corruptors"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getCorruptors()));
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits(ZergUnitLibrary.Corruptor, EcSwingX.getDigit(e));
			}
        });
        swingX.addInput(this, messages.getString("waypoint.broodlords"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getBroodlords()));
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setUnits( ZergUnitLibrary.Broodlord, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        addCheck(messages.getString("waypoint.melee") + " +1", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
                c.setSelection(state.isMelee1());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Melee1);
                else state.removeUpgrade(ZergUpgradeLibrary.Melee1);
			}
        });
        addCheck("+2", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isMelee2());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Melee2);
                else state.removeUpgrade(ZergUpgradeLibrary.Melee2);
			}
        });
        addCheck("+3", new CustomActionListener() {

            void reverse(Object o) {
            	Button c = (Button) o;
            	c.setSelection(state.isMelee3());
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Melee3);
                else state.removeUpgrade(ZergUpgradeLibrary.Melee3);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        addCheck(messages.getString("waypoint.missile") + " +1", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isMissile1());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Missile1);
                else state.removeUpgrade(ZergUpgradeLibrary.Missile1);
			}
        });
        addCheck("+2", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isMelee2());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Missile2);
                else state.removeUpgrade(ZergUpgradeLibrary.Missile2);
			}
        });
        addCheck("+3", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isMissile3());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Missile3);
                else state.removeUpgrade(ZergUpgradeLibrary.Missile3);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        addCheck(messages.getString("waypoint.carapace") + " +1", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isArmor1());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Armor1);
                else state.removeUpgrade(ZergUpgradeLibrary.Armor1);
			}
        });
        addCheck("+2", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isArmor2());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Armor2);
                else state.removeUpgrade(ZergUpgradeLibrary.Armor2);
			}
        });
        addCheck("+3", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isArmor3());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Armor3);
                else state.removeUpgrade(ZergUpgradeLibrary.Armor3);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        addCheck(messages.getString("waypoint.flyerAttack") + " +1", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isFlyerAttack1());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerAttacks1);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerAttacks1);
			}
        });
        addCheck("+2", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isFlyerAttack2());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerAttacks2);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerAttacks2);
			}
        });
        addCheck("+3", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isFlyerAttack3());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerAttacks3);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerAttacks3);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        addCheck(messages.getString("waypoint.flyerArmor") + " +1", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isFlyerArmor1());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerArmor1);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerArmor1);
			}
        });
        addCheck("+2", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isFlyerArmor2());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerArmor2);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerArmor2);
			}
        });
        addCheck("+3", new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Button c = (Button) o;
            	c.setSelection(state.isFlyerArmor3());
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(swingX.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerArmor3);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerArmor3);
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.bases"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
            	c.setText(Integer.toString(state.requiredBases));
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				state.requiredBases = EcSwingX.getDigit(e);
			}
        });
        swingX.addInput(this, messages.getString("waypoint.lairs"), NumberTextField.class, new CustomActionListener() {

            void reverse(Object o) {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getLairs()));
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setBuilding(ZergBuildingLibrary.Lair, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.hives"), NumberTextField.class, new CustomActionListener() {

            void reverse(Object o) {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getHives()));
            }

			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setBuilding(ZergBuildingLibrary.Hive, EcSwingX.getDigit(e));
			}
        });
        swingX.addInput(this, messages.getString("waypoint.gasExtractors"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getGasExtractors()));
            }
			@Override
			public void widgetSelected(SelectionEvent e) {
				state.setBuilding(ZergBuildingLibrary.Extractor, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.evolutionChambers"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getEvolutionChambers()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.EvolutionChamber, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.spineCrawlers"), NumberTextField.class, new CustomActionListener()
        {

            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getSpineCrawlers()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.SpineCrawler, EcSwingX.getDigit(e));
			}
        });
        swingX.addInput(this, messages.getString("waypoint.sporeCrawlers"), NumberTextField.class, new CustomActionListener()
        {

            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getSporeCrawlers()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.SporeCrawler, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.spawningPools"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getSpawningPools()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.SpawningPool, EcSwingX.getDigit(e));
			}
        });
        swingX.addInput(this, messages.getString("waypoint.banelingNests"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getBanelingNest()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.BanelingNest, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.roachWarrens"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getRoachWarrens()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.RoachWarren, EcSwingX.getDigit(e));
			}
        });
        swingX.addInput(this, messages.getString("waypoint.hydraliskDens"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getHydraliskDen()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.HydraliskDen, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.infestationPits"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getInfestationPit()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.InfestationPit, EcSwingX.getDigit(e));
			}
        });
        swingX.addInput(this, messages.getString("waypoint.spires"), NumberTextField.class, new CustomActionListener()
        {

            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getSpire()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.Spire, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.nydusNetworks"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getNydusNetwork()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.NydusNetwork, EcSwingX.getDigit(e));
			}
        });
        swingX.addInput(this, messages.getString("waypoint.nydusWorms"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getNydusWorm()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.NydusWorm, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;
        swingX.addInput(this, messages.getString("waypoint.ultraliskCaverns"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getUltraliskCavern()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.UltraliskCavern, EcSwingX.getDigit(e));
			}
        });
        swingX.addInput(this, messages.getString("waypoint.greaterSpires"), NumberTextField.class, new CustomActionListener()
        {
            void reverse(Object o)
            {
            	Text c = (Text) o;
                c.setText(Integer.toString(state.getGreaterSpire()));
            }
            @Override
			public void widgetSelected(SelectionEvent e) {
            	state.setBuilding(ZergBuildingLibrary.GreaterSpire, EcSwingX.getDigit(e));
			}
        });
        swingX.gridx = 0;
        swingX.margintop += swingX.topIncreament;

        int width = 4;
        final PanelWayPoint tmp = this;
        if (state != swingX.destination.get(swingX.destination.size()-1)){ //add a "remove" button for all waypoints except the final destination
        	swingX.inputControls.add(swingX.addButton(this, messages.getString("waypoint.remove"), 1, new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					swingX.removeTab(tmp);
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			}));
            width = 3;
        }
        
        swingX.inputControls.add(swingX.addButton(this, messages.getString("waypoint.reset"), width, new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Control[] controls = getChildren();
				for(int i = 0; i < controls.length; i++){
					Control control = controls[i];
					if(control instanceof Text){
						Text text = (Text) control;
						if(text.getText().indexOf(":") == -1){
							text.setText(Integer.toString(0));
							text.notifyListeners(SWT.Selection, new Event());
						}
					}else if(control instanceof Button){
						Button b = (Button) control;
						b.setSelection(false);
						b.notifyListeners(SWT.Selection, new Event());
					}
					
				}
	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		}));

    }

    String formatAsTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;

        return Integer.toString(minutes) + ":"
            + (seconds < 10 ? "0" : "")
            + Integer.toString(seconds);
    }

    public EcState getState() {
        return state;
    }
}
