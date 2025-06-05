package org.example;

import java.util.Random;
import java.util.*;

public interface HintProvider {
    String getHint();
}

class RandomHintProvider implements HintProvider {
    private final HintProvider[] providers;
    private final Random random;

    public RandomHintProvider(HintProvider... providers) {
        this.providers = providers;
        this.random = new Random();
    }

    @Override
    public String getHint() {
        int index = random.nextInt(providers.length);
        return providers[index].getHint();
    }
}



class DailyScrumHelpHintProvider implements HintProvider {
    private static final String[] HELP_HINTS = {
            "Daily Scrum is bedoeld voor korte statusupdates: gisteren, vandaag, obstakels."
    };

    @Override
    public String getHint() {
        return HELP_HINTS[new Random().nextInt(HELP_HINTS.length)];
    }
}

class DailyScrumFunnyHintProvider implements HintProvider {
    private static final String[] FUNNY_HINTS = {
            "Als je standup langer duurt dan je koffie warm blijft, doe je iets fout!"
    };

    @Override
    public String getHint() {
        return FUNNY_HINTS[new Random().nextInt(FUNNY_HINTS.length)];
    }
}

class ScrumBoardHelpHintProvider implements HintProvider {
    private static final String[] HELP_HINTS = {
            "Een Epic is een groot gebruikersverhaal, een User Story beschrijft een gebruikersdoel, en een Taak is een uitvoerbare eenheid.",
            "Scrum Boards helpen om werk visueel te volgen door kolommen zoals To Do, In Progress, en Done."
    };

    @Override
    public String getHint() {
        return HELP_HINTS[new Random().nextInt(HELP_HINTS.length)];
    }
}

class ScrumBoardFunnyHintProvider implements HintProvider {
    private static final String[] FUNNY_HINTS = {
            "Als alles in 'In Progress' blijft hangen, is het tijd voor een stand-up én koffie!",
            "Een taak zonder eigenaar is als een scrum board zonder post-its: leeg en zielig."
    };

    @Override
    public String getHint() {
        return FUNNY_HINTS[new Random().nextInt(FUNNY_HINTS.length)];
    }
}
class SprintPlanningHelpHintProvider implements HintProvider {
    private static final String[] HELP_HINTS = {
            "Tijdens Sprint Planning kies je werk dat past binnen de teamcapaciteit en het sprintdoel.",
            "Velocity helpt het team inschatten hoeveel werk haalbaar is in de sprint."
    };

    @Override
    public String getHint() {
        return HELP_HINTS[new Random().nextInt(HELP_HINTS.length)];
    }
}

class SprintPlanningFunnyHintProvider implements HintProvider {
    private static final String[] FUNNY_HINTS = {
            "Als je sprintplanning voelt als een gokspel, is het tijd om beter te schatten!",
            "Het toevoegen van werk tijdens de sprint? Alleen als je dol bent op chaos."
    };

    @Override
    public String getHint() {
        return FUNNY_HINTS[new Random().nextInt(FUNNY_HINTS.length)];
    }
}
class SprintRetrospectiveHelpHintProvider implements HintProvider {
    private static final String[] HELP_HINTS = {
            "Een Retrospective is bedoeld om te reflecteren en samen te verbeteren.",
            "Goede Retrospectives richten zich op samenwerking, communicatie en processen."
    };

    @Override
    public String getHint() {
        return HELP_HINTS[new Random().nextInt(HELP_HINTS.length)];
    }
}

class SprintRetrospectiveFunnyHintProvider implements HintProvider {
    private static final String[] FUNNY_HINTS = {
            "Retrospective: de enige meeting waar klagen mag (als het constructief is).",
            "Wat in de retro gezegd wordt, blijft in de retro. Behalve als het hilarisch is."
    };

    @Override
    public String getHint() {
        return FUNNY_HINTS[new Random().nextInt(FUNNY_HINTS.length)];
    }
}
class SprintReviewHelpHintProvider implements HintProvider {
    private static final String[] HELP_HINTS = {
            "De Sprint Review is bedoeld om werk te tonen en feedback te verzamelen van stakeholders.",
            "Na een review kunnen de product backlog en doelen aangepast worden op basis van feedback."
    };

    @Override
    public String getHint() {
        return HELP_HINTS[new Random().nextInt(HELP_HINTS.length)];
    }
}

class SprintReviewFunnyHintProvider implements HintProvider {
    private static final String[] FUNNY_HINTS = {
            "Laat in de review geen PowerPoint zien zonder werk — de PO's vallen dan in slaap.",
            "De demo werkt niet? De stakeholder ziet het als een feature, geen bug!"
    };

    @Override
    public String getHint() {
        return FUNNY_HINTS[new Random().nextInt(FUNNY_HINTS.length)];
    }
}

class TIAKamerHelpHintProvider implements HintProvider {
    private static final String[] HELP_HINTS = {
            ""
    };
    @Override
    public String getHint() {
        return HELP_HINTS[new Random().nextInt(HELP_HINTS.length)];
    }
}

class TIAKamerFunnyHintProvider implements HintProvider {
    private static final String[] FUNNY_HINTS = {
            ""
    };
    @Override
    public String getHint() {
        return FUNNY_HINTS[
                new Random().nextInt(FUNNY_HINTS.length)];
    }
}

class VoorwerpenKamerHelpHintProvider implements HintProvider {
    private static final String[] HELP_HINTS = {
            ""
    };
    @Override
    public String getHint() {
        return HELP_HINTS[new Random().nextInt(HELP_HINTS.length)];
    }
}
class VoorwerpenKamerFunnyHintProvider implements HintProvider {
    private static final String[] FUNNY_HINTS = {
            ""
    };
    @Override
    public String getHint() {
        return FUNNY_HINTS[new Random().nextInt(FUNNY_HINTS.length)];
    }
}

