package com.gutterboys.riichi.calculator.yaku.standard;

import com.gutterboys.riichi.calculator.model.GameContext;
import com.gutterboys.riichi.calculator.model.PossibleHand;

public class Sankantsu implements StandardStructureYaku {
    
        @Override
        public void execute(GameContext gameContext, PossibleHand possibleHand) {
            if (gameContext.getKanCount() < 3) {
                return;
            }  
            possibleHand.setHan(possibleHand.getHan() + 2);
            possibleHand.getQualifiedYaku().add("Sankantsu (Three Kans)");             
        }
    
    
}
