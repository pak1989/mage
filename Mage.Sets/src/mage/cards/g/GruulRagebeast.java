
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class GruulRagebeast extends CardImpl {

    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public GruulRagebeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Gruul Ragebeast or another creature enters the battlefield under your control, that creature fights target creature an opponent controls.
        Ability ability = new GruulRagebeastTriggeredAbility();

        ability.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability);
    }

    public GruulRagebeast(final GruulRagebeast card) {
        super(card);
    }

    @Override
    public GruulRagebeast copy() {
        return new GruulRagebeast(this);
    }
}

class GruulRagebeastTriggeredAbility extends TriggeredAbilityImpl {

    GruulRagebeastTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GruulRagebeastEffect(), false);
    }

    GruulRagebeastTriggeredAbility(final GruulRagebeastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GruulRagebeastTriggeredAbility copy() {
        return new GruulRagebeastTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        if (permanent.isControlledBy(this.controllerId)
                && permanent.isCreature()
                && (targetId.equals(this.getSourceId())
                || !targetId.equals(this.getSourceId()))) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature enters the battlefield under your control, that creature fights target creature an opponent controls.";
    }
}

class GruulRagebeastEffect extends OneShotEffect {

    GruulRagebeastEffect() {
        super(Outcome.Damage);
    }

    GruulRagebeastEffect(final GruulRagebeastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.isCreature()
                && target.isCreature()) {
            return triggeredCreature.fight(target, source, game);
        }
        return false;
    }

    @Override
    public GruulRagebeastEffect copy() {
        return new GruulRagebeastEffect(this);
    }
}
