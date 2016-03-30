package com.fray.evo.util;

public final class TerranUnitLibrary extends Library<Unit> {
    public static final Unit SCV = TerranLibrary.SCV;
    public static final Unit MULE = TerranLibrary.MULE;
    public static final Unit Marine = TerranLibrary.Marine;
    public static final Unit Marauder = TerranLibrary.Marauder;
    public static final Unit Reaper = TerranLibrary.Reaper;
    public static final Unit Ghost = TerranLibrary.Ghost;
    public static final Unit Hellion = TerranLibrary.Hellion;
    public static final Unit SiegeTank = TerranLibrary.SiegeTank;
    public static final Unit Thor = TerranLibrary.Thor;
    public static final Unit Viking = TerranLibrary.Viking;
    public static final Unit Medivac = TerranLibrary.Medivac;
    public static final Unit Raven = TerranLibrary.Raven;
    public static final Unit Banshee = TerranLibrary.Banshee;
    public static final Unit Battlecruiser = TerranLibrary.Battlecruiser;

    private TerranUnitLibrary() {
        super();
		libraryList.add(SCV);
		libraryList.add(MULE);
		libraryList.add(Marine);
		libraryList.add(Marauder);
		libraryList.add(Reaper);
		libraryList.add(Ghost);
		libraryList.add(Hellion);
		libraryList.add(SiegeTank);
		libraryList.add(Thor);
		libraryList.add(Viking);
		libraryList.add(Medivac);
		libraryList.add(Raven);
		libraryList.add(Banshee);
		libraryList.add(Battlecruiser);

        for (Unit unit : libraryList) {
            idToItemMap.put(unit.getId(), unit);
        }
    }

    private static final TerranUnitLibrary instance = new TerranUnitLibrary();
    public static final TerranUnitLibrary getInstance() {
        return instance;
    }
}
