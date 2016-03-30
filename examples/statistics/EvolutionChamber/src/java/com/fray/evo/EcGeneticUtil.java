package com.fray.evo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Logger;

import org.jgap.Gene;
import org.jgap.GeneticOperator;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.IntegerGene;
import org.jgap.util.ICloneable;

import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildOverlord;

public final class EcGeneticUtil
{
	private static final Logger logger = Logger.getLogger(EcGeneticUtil.class.getName());
	static GeneticOperator getDeletionOperator(final EvolutionChamber c)
	{
		return new GeneticOperator()
		{
			@Override
			public void operate(Population arg0, List arg1)
			{
				for (int i = 0; i < arg0.size(); i++)
				{
					if (Math.random() > c.getBaseMutationRate()/c.getChromosomeLength())
						continue;
					IChromosome chromosome = (IChromosome) ((ICloneable) arg0.getChromosome(i)).clone();
					Gene[] beforeArray = chromosome.getGenes();
					for (int j = (int) (Math.random() * beforeArray.length); j < beforeArray.length - 1; j++)
						beforeArray[j] = beforeArray[j + 1];
					try
					{
						chromosome.setGenes(beforeArray);
					}
					catch (InvalidConfigurationException e)
					{
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						logger.severe(sw.toString());
					}
					arg1.add(chromosome);
				}
			}
		};
	}

	static GeneticOperator getCleansingOperator(final EvolutionChamber c)
	{
		return new GeneticOperator()
		{
			@Override
			public void operate(Population arg0, List arg1)
			{
				if (Math.random() > c.getBaseMutationRate()/c.getChromosomeLength())
					return;
				IChromosome best = arg0.determineFittestChromosome();
				for (int i = 0; i < best.getGenes().length; i++)
				{
					IChromosome chromosome = (IChromosome) ((ICloneable) best).clone();
					Gene[] beforeArray = chromosome.getGenes();
					for (int j = (int) i; j < beforeArray.length - 1; j++)
						beforeArray[j].setAllele(beforeArray[j + 1].getAllele());
					try
					{
						chromosome.setGenes(beforeArray);
					}
					catch (InvalidConfigurationException e)
					{
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						logger.severe(sw.toString());
					}
					arg1.add(chromosome);
				}
			}
		};
	}

	static GeneticOperator getOverlordingOperator(final EvolutionChamber c, final List<Class<? extends EcAction>> actions)
	{
		return new GeneticOperator()
		{
			@Override
			public void operate(Population arg0, List arg1)
			{
				if (Math.random() > c.getBaseMutationRate()/c.getChromosomeLength())
					return;
				IChromosome best = arg0.determineFittestChromosome();
				
				// TODO expects that there is ALWAYS an overlord action which won't work for other races. But then, this is the Overlording action.
				int overlord = actions.indexOf(EcActionBuildOverlord.class);			
				
				for (int i = 0; i < best.getGenes().length; i++)
				{
					IChromosome chromosome = (IChromosome) ((ICloneable) best).clone();
					Gene[] beforeArray = chromosome.getGenes();
					beforeArray[i].setAllele(overlord);
					try
					{
						chromosome.setGenes(beforeArray);
					}
					catch (InvalidConfigurationException e)
					{
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						logger.severe(sw.toString());
					}
//					EvolutionChamber.displayChromosome(chromosome);
//					System.out.println();
					arg1.add(chromosome);
				}
			}
		};
	}
	static GeneticOperator getInsertionOperator(final EvolutionChamber c)
	{
		return new GeneticOperator()
		{
			@Override
			public void operate(Population arg0, List arg1)
			{
				for (int i = 0; i < arg0.size(); i++)
				{
					if (Math.random() > c.getBaseMutationRate()/c.getChromosomeLength())
						continue;
					IChromosome chromosome = (IChromosome) ((ICloneable) arg0.getChromosome(i)).clone();
					Gene[] beforeArray = chromosome.getGenes();
					int randomPoint = (int) (Math.random() * beforeArray.length);
					for (int j = randomPoint; j < beforeArray.length - 1; j++)
						beforeArray[j + 1] = beforeArray[j];
					beforeArray[randomPoint].setAllele((int) (Math.random() * ((IntegerGene) chromosome
							.getGene(randomPoint)).getUpperBounds()));
					try
					{
						chromosome.setGenes(beforeArray);
					}
					catch (InvalidConfigurationException e)
					{
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						logger.severe(sw.toString());
					}
					arg1.add(chromosome);
				}
			}
		};
	}

	private static GeneticOperator getLengthenOperator(final EvolutionChamber c)
	{
		return new GeneticOperator()
		{
			@Override
			public void operate(Population arg0, List arg1)
			{
				for (int i = 0; i < arg0.size(); i++)
				{
				if (Math.random() > c.getBaseMutationRate()/c.getChromosomeLength())
					continue;
					IChromosome chromosome = arg0.getChromosome(i);
					Gene[] beforeArray = chromosome.getGenes();
					Gene[] afterArray = new Gene[beforeArray.length + 1];
					for (int j = 0; j < beforeArray.length; j++)
						afterArray[j] = beforeArray[j];
					afterArray[afterArray.length - 1] = afterArray[0].newGene();
					try
					{
						chromosome.setGenes(afterArray);
					}
					catch (InvalidConfigurationException e)
					{
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						logger.severe(sw.toString());
					}
				}
				IChromosome chromosome = arg0.getConfiguration().getSampleChromosome();
				Gene[] beforeArray = chromosome.getGenes();
				Gene[] afterArray = new Gene[beforeArray.length + 1];
				for (int j = 0; j < beforeArray.length; j++)
					afterArray[j] = beforeArray[j];
				afterArray[afterArray.length - 1] = afterArray[0].newGene();
				try
				{
					chromosome.setGenes(afterArray);
				}
				catch (InvalidConfigurationException e)
				{
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					logger.severe(sw.toString());
				}
			}
		};
	}

	private static GeneticOperator getShortenOperator(final EvolutionChamber c)
	{
		return new GeneticOperator()
		{
			@Override
			public void operate(Population arg0, List arg1)
			{
				if (Math.random() > c.getBaseMutationRate()/c.getChromosomeLength())
					return;
				for (int i = 0; i < arg0.size(); i++)
				{
					IChromosome chromosome = arg0.getChromosome(i);
					Gene[] beforeArray = chromosome.getGenes();
					Gene[] afterArray = new Gene[beforeArray.length - 1];
					for (int j = 0; j < afterArray.length; j++)
						afterArray[j] = beforeArray[j];
					try
					{
						chromosome.setGenes(afterArray);
					}
					catch (InvalidConfigurationException e)
					{
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						logger.severe(sw.toString());
					}
				}
				IChromosome chromosome = arg0.getConfiguration().getSampleChromosome();
				Gene[] beforeArray = chromosome.getGenes();
				Gene[] afterArray = new Gene[beforeArray.length - 1];
				for (int j = 0; j < afterArray.length; j++)
					afterArray[j] = beforeArray[j];
				try
				{
					chromosome.setGenes(afterArray);
				}
				catch (InvalidConfigurationException e)
				{
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					logger.severe(sw.toString());
				}
			}
		};
	}

	static GeneticOperator getSwapOperator(final EvolutionChamber c)
	{
		return new GeneticOperator()
		{
			@Override
			public void operate(Population arg0, List arg1)
			{
				for (int i = 0; i < arg0.size(); i++)
				{
					if (Math.random() > c.getBaseMutationRate()/c.getChromosomeLength())
						continue;
					IChromosome chromosome = (IChromosome) ((ICloneable) arg0.getChromosome(i)).clone();
					Gene[] beforeArray = chromosome.getGenes();
					int randomPoint = (int) (Math.random() * beforeArray.length);
					int randomPoint2 = (int) (Math.random() * beforeArray.length);
					Gene swap = beforeArray[randomPoint];
					beforeArray[randomPoint] = beforeArray[randomPoint2];
					beforeArray[randomPoint2] = swap;
					try
					{
						chromosome.setGenes(beforeArray);
					}
					catch (InvalidConfigurationException e)
					{
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						logger.severe(sw.toString());
					}
					arg1.add(chromosome);
				}
			}
		};
	}

	static GeneticOperator getTwiddleOperator(final EvolutionChamber c)
	{
		return new GeneticOperator()
		{
			@Override
			public void operate(Population arg0, List arg1)
			{
				for (int i = 0; i < arg0.size(); i++)
				{
					if (Math.random() > c.getBaseMutationRate()/c.getChromosomeLength())
						continue;
					IChromosome chromosome = (IChromosome) ((ICloneable) arg0.getChromosome(i)).clone();
					Gene[] beforeArray = chromosome.getGenes();
					int randomPoint = (int) (Math.random() * beforeArray.length);
					if (randomPoint < beforeArray.length - 1)
					{
						Gene swap = beforeArray[randomPoint];
						beforeArray[randomPoint] = beforeArray[randomPoint + 1];
						beforeArray[randomPoint + 1] = swap;
					}
					try
					{
						chromosome.setGenes(beforeArray);
					}
					catch (InvalidConfigurationException e)
					{
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						logger.severe(sw.toString());
					}
					arg1.add(chromosome);
				}
			}
		};
	}

}
