import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import Table from './Table';
import {AiOutlineEye, AiOutlineEdit, AiOutlineDelete, AiFillStar, AiOutlineStar, AiOutlineSearch} from 'react-icons/ai';
import Chips from 'react-chips';
import './TableContainer.css';
import ConfirmationModal from "./ConfirmationModal";
import {orderApi} from "../misc/OrderApi";
import {urlPaths} from "../../Constants";

const ArchivedTableContainer = () => {
    const navigate = useNavigate();
    const [tags, setTags] = useState([]);
    const [showSearchBar, setShowSearchBar] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [selectedRowData, setSelectedRowData] = useState(null);
    const [user, setUser] = useState(null)
    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('userDetails'))
        setUser(storedUser)
        const userJson = JSON.parse(localStorage.getItem('user'))
        const archivedJobs = JSON.parse(localStorage.getItem('archivedJobs'));
        const fetchData = async () => {
            try {
                // const response = await orderApi.getApiCall(userJson,urlPaths.GET_ARCHIVED_JOB_APPLICATIONS + storedUser.userId);
                setData(archivedJobs);
                // console.log('API Response:', response);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        fetchData();
    }, []);

    const dataJson = [
        {
            id: 1,
            star: true,
            companyName: 'Company A',
            role: 'Developer',
            appliedOn: '2024-03-01',
            status: 'Offer',
            notes: 'notes1',
            field2: 'filed2',
            tags: ['tag1', 'tag2'],
            jobUrl: 'https://blog.logrocket.com/react-table-complete-guide/'
        },
        {
            id: 2,
            star: true,
            companyName: 'Company B',
            role: 'Tester',
            appliedOn: '2024-04-01',
            status: 'Applied',
            notes: 'notes2',
            field2: 'filed2',
            tags: ['tag3', 'tag4'],
            jobUrl: 'https://www.npmjs.com/package/react-chips'
        },
    ];
    const [data, setData]=useState([]);





    const columns = [
        {
            Header: 'Starred',
            accessor: 'star',
            Cell: ({ row }) => (
                <div>
                    {row.original.star ? <AiFillStar className="action-icon"/> : <AiOutlineStar className="action-icon"/>}
                </div>
            )
        },
        // { Header: 'Company Name', accessor: 'company' },
        {
            Header: 'Company Name',
            accessor: 'companyUrl',
            Cell: ({ row }) => (
                <a href={row.original.companyUrl} target="_blank" rel="noopener noreferrer">
                    {row.original.company}
                </a>
            )
        },
        { Header: 'Role', accessor: 'position' },
        { Header: 'Applied On', accessor: 'applicationDate' },
        { Header: 'Status', accessor: 'status' },
        {
            Header: 'Tags',
            accessor: 'jobTags',
            Cell: ({ value }) => (
                <div className="tags-cell">
                    {value.map((tag, index) => (
                        <span key={index} className={`tag tag-${index % 4}`}>
              {tag.name}
            </span>
                    ))}
                </div>
            ),
        },
        {
            Header: 'Actions',
            accessor: 'actions',
            Cell: ({ row }) => (
                <>
                    <AiOutlineEye onClick={() => handleView(row.original)} className="action-icon" />
                    <AiOutlineDelete onClick={() => handleDelete(row.original)} className="action-icon" />
                </>
            )
        }
    ];

    const handleView = (rowData) => {
        console.log('View:', rowData);
        navigate('/details', { state: { rowData } });
    };

    const handleEdit = (rowData) => {
        console.log('Edit:', rowData);
        navigate('/edit', { state: { rowData } });
    };

    const handleDelete = (rowData) => {
        setSelectedRowData(rowData);
        setShowDeleteModal(true);
    };

    const confirmDelete = async () => {
        console.log('Delete:', selectedRowData);
        try {
            const storedUser = JSON.parse(localStorage.getItem('userDetails'))
            const userJson = JSON.parse(localStorage.getItem('user'))
            const response = await orderApi.deleteApiCall(userJson, urlPaths.UNARCHIVE_JOB_APPLICATION + storedUser.userId+`/${selectedRowData.id}`);
            console.log('API Response:', response);
            const updatedData = data.filter(item => item.id !== selectedRowData.id);
            localStorage.setItem('archivedJobs', JSON.stringify(updatedData));
            const unArchivedJobs = JSON.parse(localStorage.getItem('unArchivedJobs'))
            unArchivedJobs.push(selectedRowData);
            localStorage.setItem('unArchivedJobs', JSON.stringify(unArchivedJobs));
            setData(updatedData);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
        setShowDeleteModal(false);
    };


    const handleChange = (newTags) => {
        setTags(newTags);
    };

    const toggleSearchBar = () => {
        setShowSearchBar(!showSearchBar);
    };

    const filteredData = tags.length > 0
        ? data.filter((item) => tags.every((tag) => item.tags.includes(tag)))
        : data;

    return (
        <div className="search-table-container">
            <div className="search-container">
                <button className="search-button" onClick={toggleSearchBar}>
                    <AiOutlineSearch className="search-icon"/>
                    <span className="search-text">Search by Tags</span>
                </button>
                {showSearchBar && (
                    <div className="search-bar">
                        <Chips
                            value={tags}
                            onChange={handleChange}
                            suggestions={['tag1', 'tag2', 'tag3', 'tag4']}
                            placeholder="Type a tag and press enter..."
                            className="react-chips"
                        />
                    </div>
                )}
            </div>
            <div className="table-container">
            <Table data={filteredData} columns={columns} iconStyle={{fontSize: '24px', marginRight: '12px'}}/>
            </div>
            <ConfirmationModal show={showDeleteModal} onHide={() => setShowDeleteModal(false)} onConfirm={confirmDelete} rowData={selectedRowData} />
        </div>
    );
};

export default ArchivedTableContainer;