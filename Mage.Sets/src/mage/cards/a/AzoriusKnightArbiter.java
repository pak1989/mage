package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AzoriusKnightArbiter extends CardImpl {

    public AzoriusKnightArbiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Azorius Knight-Arbiter can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private AzoriusKnightArbiter(final AzoriusKnightArbiter card) {
        super(card);
    }

    @Override
    public AzoriusKnightArbiter copy() {
        return new AzoriusKnightArbiter(this);
    }
}
