/*
 * @(#)SiadapStateToShowInCount.java
 *
 * Copyright 2012 Instituto Superior Tecnico
 * Founding Authors: Paulo Abrantes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the SIADAP Module.
 *
 *   The SIADAP Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The SIADAP Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the SIADAP Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.siadap.presentationTier.renderers.providers;

import java.util.ArrayList;

import module.siadap.domain.SiadapProcessStateEnum;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

/**
 * 
 * @author João Antunes
 * 
 */
public class SiadapStateToShowInCount implements DataProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * pt.ist.fenixWebFramework.renderers.DataProvider#provide(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public Object provide(Object source, Object currentValue) {
        ArrayList<SiadapProcessStateEnum> siadapProcessStateEnumToReturn = new ArrayList<SiadapProcessStateEnum>();
        for (SiadapProcessStateEnum stateEnum : SiadapProcessStateEnum.values()) {
            if (stateEnum.ordinal() < getMaximumStateToShowInCount().ordinal()) {
                siadapProcessStateEnumToReturn.add(stateEnum);
            }
        }
        return siadapProcessStateEnumToReturn;
    }

    public static SiadapProcessStateEnum getMaximumStateToShowInCount() {
        return SiadapProcessStateEnum.FINAL_STATE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see pt.ist.fenixWebFramework.renderers.DataProvider#getConverter()
     */
    @Override
    public Converter getConverter() {
        return null;
        // return new Converter() {
        //
        // @Override
        // public Object convert(Class type, Object value) {
        // if (value != null)
        // return ((SiadapYearConfiguration) value).getYear();
        // return null;
        // }
        // };
    }

    public static SiadapProcessStateEnum getDefaultStateToFilter() {
        return SiadapProcessStateEnum.WAITING_SELF_EVALUATION;
    }

}
