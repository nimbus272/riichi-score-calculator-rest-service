package com.gutterboys.riichi.calculator.yaku.last;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.gutterboys.riichi.calculator.constants.RiichiCalculatorConstants;
import com.gutterboys.riichi.calculator.exception.RiichiCalculatorException;
import com.gutterboys.riichi.calculator.helper.CommonUtil;
import com.gutterboys.riichi.calculator.model.RiichiCalculatorRequest;
import com.gutterboys.riichi.calculator.model.PossibleHand;

@Component
public class ChantaOrJunchan implements LastYaku {

    @Override
    public void execute(RiichiCalculatorRequest request, PossibleHand possibleHand) throws RiichiCalculatorException {
        if (possibleHand.getMelds().stream().filter(meld -> CommonUtil.isTerminalChi(meld)).count() > 0) {
            List<List<Integer>> melds = possibleHand.getMelds();
            int validMelds = 0;
            for (int i = 0; i < melds.size(); i++) {
                List<Integer> meld = melds.get(i);
                if (CollectionUtils.containsAny(meld, RiichiCalculatorConstants.TERMINAL_AND_HONOR)) {
                    validMelds++;
                }
            }
            if (validMelds == melds.size()) {
                List<Integer> tiles = possibleHand.getTiles();
                if (CollectionUtils.containsAny(tiles, RiichiCalculatorConstants.HONORS)) {
                    if (request.isOpened()) {
                        possibleHand.setHan(possibleHand.getHan() + 1);
                        possibleHand.getQualifiedYaku().add("Chanta (Terminal or Honor in Each Set)");
                    } else {
                        possibleHand.setHan(possibleHand.getHan() + 2);
                        possibleHand.getQualifiedYaku().add("Chanta (Terminal or Honor in Each Set)");
                    }
                } else {
                    if (request.isOpened()) {
                        possibleHand.setHan(possibleHand.getHan() + 2);
                        possibleHand.getQualifiedYaku().add("Junchan (Terminal in Each Set)");
                    } else {
                        possibleHand.setHan(possibleHand.getHan() + 3);
                        possibleHand.getQualifiedYaku().add("Junchan (Terminal in Each Set)");
                    }
                }
            }
        }
    }

}
