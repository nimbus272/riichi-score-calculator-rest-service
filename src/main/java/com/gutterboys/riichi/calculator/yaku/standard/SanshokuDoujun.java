
package com.gutterboys.riichi.calculator.yaku.standard;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class SanshokuDoujun implements StandardStructureYaku {
    @Override
    public void execute(GameContext gameContext, PossibleHand possibleHand) {
        for (int i = 0; i < possibleHand.getMelds().size(); i++) {
            List<Integer> meld = possibleHand.getMelds().get(i);
            if (CommonUtil.isChi(meld)) {
                int index1 = CommonUtil.getIndexFromTile(meld.get(0));
                if (possibleHand.getMelds().stream().filter(x -> {
                    if (CommonUtil.isChi(x)) {
                        int index2 = CommonUtil.getIndexFromTile(x.get(0));
                        return index1 == index2;
                    }
                    return false;
                }).count() > 2L) {
                    if (gameContext.isOpened()) {
                        possibleHand.setHan(possibleHand.getHan() + 1);
                    } else {
                        possibleHand.setHan(possibleHand.getHan() + 2);
                    }
                    possibleHand.getQualifiedYaku().add("Sanshoku Doujun (Same Sequence in All Suits)");
                    return;
                }
            }
        }
    }

}
