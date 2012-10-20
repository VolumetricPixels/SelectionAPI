package me.dzineit.selectionapi;

import org.spout.api.component.components.EntityComponent;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;

public class SelectionComponent extends EntityComponent {
    private Selection selection;

    @Override
    public void onAttached() {
        Entity player = getOwner();

        if (player instanceof Player) {
            selection = new Selection(((Player) player).getName(), player.getWorld());
        }
    }

    public Selection getSelection() {
        return selection;
    }
}
