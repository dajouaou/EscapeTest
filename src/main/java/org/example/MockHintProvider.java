package org.example;

public class MockHintProvider implements HintProvider {
    private boolean aangeroepen = false;

    @Override
    public String getHint() {
        aangeroepen = true;
        return "Mock hint: test succesvol";
    }

    public boolean isAangeroepen() {
        return aangeroepen;
    }
}
