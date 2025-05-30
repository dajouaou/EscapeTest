package org.example;

import java.util.Random;

public interface HintProvider {
    String getHint();
}

class HelpHintProvider implements HintProvider {
    private static final String[] HELP_HINTS = {
            "Een standup duurt maximaal 15 minuten",
            "Denk aan de Scrum-waarden: Focus, Openheid, Respect, Moed en Toewijding",
            "De Product Owner is verantwoordelijk voor de Product Backlog",
            "Scrum Board kolommen zijn typisch: To Do, In Progress, Done",
            "Een Sprint Retrospective is voor teamreflectie en verbetering"
    };

    @Override
    public String getHint() {
        return HELP_HINTS[new Random().nextInt(HELP_HINTS.length)];
    }
}

class FunnyHintProvider implements HintProvider {
    private static final String[] FUNNY_HINTS = {
            "Voortaan niet hele avonden gamen, studeren is beter voor je!",
            "Zelfs een kip kan Scrummen, maar jij blijkbaar nog niet!",
            "Heb je geprobeerd de computer uit en weer aan te zetten?",
            "Als Scrum zo makkelijk was, deed iedereen het wel!",
            "Niet getreurd, zelfs Einstein had moeite met Scrum!"
    };

    @Override
    public String getHint() {
        return FUNNY_HINTS[new Random().nextInt(FUNNY_HINTS.length)];
    }
}

class RandomHintProvider implements HintProvider {
    private final HintProvider[] providers;

    public RandomHintProvider(HintProvider... providers) {
        this.providers = providers;
    }

    @Override
    public String getHint() {
        return providers[new Random().nextInt(providers.length)].getHint();
    }
}