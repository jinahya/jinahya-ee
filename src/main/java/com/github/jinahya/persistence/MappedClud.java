/*
 * Copyright 2013 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.jinahya.persistence;


import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
@MappedSuperclass
@XmlTransient
public abstract class MappedClud implements Clud {


    /**
     * Returns a safe copy of given date instance.
     *
     * @param date the date instance.
     *
     * @return a safe copy of given date
     */
    private static Date copy(final Date date) {

        return date == null ? null : new Date(date.getTime());
    }


    // -------------------------------------------------------------- CREATED_AT
    @Override
    public Date getCreatedAt() {

        return copy(createdAt);
    }


    @Override
    public void setCreatedAt(final Date createdAt) {

        this.createdAt = copy(createdAt);
    }


    // -------------------------------------------------------------- LOCATED_AT
    @Override
    public Date getLocatedAt() {

        return copy(locatedAt);
    }


    @Override
    public void setLocatedAt(final Date locatedAt) {

        this.locatedAt = copy(locatedAt);
    }


    // -------------------------------------------------------------- UPDATED_AT
    @Override
    public Date getUpdatedAt() {

        return copy(updatedAt);
    }


    @Override
    public void setUpdatedAt(final Date updatedAt) {

        this.updatedAt = copy(updatedAt);
    }


    // -------------------------------------------------------------- DELETED_AT
    @Override
    public Date getDeletedAt() {

        return copy(deletedAt);
    }


    @Override
    public void setDeletedAt(final Date deletedAt) {

        this.deletedAt = copy(deletedAt);
    }


    @PrePersist
    private void persisting() {

        if (createdAt == null) {
            createdAt = new Date();
        }
    }


    @Basic(optional = false)
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    @Basic
    @Column(name = "LOCATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date locatedAt;


    @Basic
    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    @Basic
    @Column(name = "DELETED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;


}

