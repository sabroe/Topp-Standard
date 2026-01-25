/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.domain.commerce.persistence.model.core;

import com.yelstream.topp.standard.domain.commerce.persistence.model.core.order.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The buying / invoicing entity that owns commercial history.
 * <p>
 * This is the central commercial party in the system — representing either an individual consumer (B2C)
 * or a company/organization (B2B). It acts as the root aggregate for carts, orders, addresses, users,
 * and future pricing agreements or credit information.
 * </p>
 * <p>
 * Main attributes include type distinction, identification data, contact information and defaults.
 * It maintains one-to-many relationships to users (especially relevant in B2B), addresses, carts and orders.
 * </p>
 * <p>
 * This entity participates in the following key business flows and interactions:
 * </p>
 * <ol>
 *     <li>Registration / onboarding of new buyers (B2C guest → individual account, B2B company setup)</li>
 *     <li>User authentication and session context establishment (login → account context)</li>
 *     <li>Cart creation and persistence (guest, anonymous, or account-bound)</li>
 *     <li>Order placement and ownership (account is the legal / invoicing party)</li>
 *     <li>Address management (default company addresses vs per-order overrides)</li>
 *     <li>Future: Quote negotiation, credit checks, invoice issuing, pricing rule assignment</li>
 * </ol>
 */
@Entity(name="account")
@Table
public class Account {

    @Id
    @GeneratedValue
    @Getter
    private UUID id;  //TO-DO: Consider ULID, TSID (time-sortable, monotonic UUID-like), UUID v7 (new in Java 21+, time-based+random)

    // @PrePersist
    // private void ensureId() {
    //     if (id == null) id = UUID.randomUUID();
    // }

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    @Setter
    private AccountType accountType;

    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String vatId;

    @Getter
    @Setter
    private String defaultCurrencyIso;

    /**
     * Extended properties.
     */
    @Getter
    @ElementCollection
    @CollectionTable(name = "product_properties")
    @MapKeyColumn(name = "property_key")
    @Column(name = "property_value")
    private Map<String, String> extendedProperties;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @Getter
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @Getter
    private List<Order> orders = new ArrayList<>();
}
