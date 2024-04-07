import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import Table from './Table';
import {AiOutlineEye, AiOutlineEdit, AiOutlineDelete, AiFillStar, AiOutlineStar, AiOutlineSearch} from 'react-icons/ai';
import Chips from 'react-chips';
import './TableContainer.css';
import ConfirmationModal from "./ConfirmationModal";
import {orderApi} from "../misc/OrderApi";
import {urlPaths} from "../../Constants";

const TableContainer = () => {
    const navigate = useNavigate();
    const [tags, setTags] = useState([]);
    const [showSearchBar, setShowSearchBar] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [selectedRowData, setSelectedRowData] = useState(null);
    const [data, setData] = useState([]);
    const [user, setUser] = useState(null);
    const [filteredData, setFilteredData] = useState([]);

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('userDetails'))
        setUser(storedUser)
        const userJson = JSON.parse(localStorage.getItem('user'))
        const unArchivedJobs = JSON.parse(localStorage.getItem('unArchivedJobs'));
        console.log(unArchivedJobs)
        setData(unArchivedJobs);
    }, []);




    const columns = [
        {
            Header: 'Starred',
            accessor: 'star',
            Cell: ({row}) => (
                <div>
                    {row.original.star ? <AiFillStar className="action-icon"/> :
                        <AiOutlineStar className="action-icon"/>}
                </div>
            )
        },
        {
            Header: 'Company Name',
            accessor: 'companyUrl',
            Cell: ({row}) => (
                <a href={row.original.companyUrl} target="_blank" rel="noopener noreferrer">
                    {row.original.company}
                </a>
            )
        },
        {Header: 'Role', accessor: 'position'},
        {Header: 'Applied On', accessor: 'applicationDate'},
        {Header: 'Status', accessor: 'status'},
        {
            Header: 'Tags',
            accessor: 'jobTags',
            Cell: ({value}) => (
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
            Cell: ({row}) => (
                <>
                    <AiOutlineEye onClick={() => handleView(row.original)} className="action-icon"/>
                    <AiOutlineEdit onClick={() => handleEdit(row.original)} className="action-icon"/>
                    <AiOutlineDelete onClick={() => handleDelete(row.original)} className="action-icon"/>
                </>
            )
        }
    ];

    const handleView = (rowData) => {
        console.log('View:', rowData);
        navigate('/details', {state: {rowData}});
    };

    const handleEdit = (rowData) => {
        console.log('Edit:', rowData);
        navigate('/edit', {state: {rowData}});
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
            const response = await orderApi.deleteApiCall(userJson, urlPaths.DELETE_JOB_APPLICATION + storedUser.userId + `/${selectedRowData.id}`);
            console.log('API Response:', response);
            const updatedData = data.filter(item => item.id !== selectedRowData.id);
            localStorage.setItem('unArchivedJobs', JSON.stringify(updatedData));
            const archivedJobs = JSON.parse(localStorage.getItem('archivedJobs'))
            archivedJobs.push(selectedRowData);
            localStorage.setItem('archivedJobs', JSON.stringify(archivedJobs));
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

    useEffect(() => {
        console.log(data)
        const filtered = tags.length > 0
            ? data.filter((item) => tags.every((tag) => item.tags.includes(tag)))
            : data;
        console.log(filtered);
        setFilteredData(filtered);
    }, [tags, data]);

    // const filteredData = tags.length > 0
    //     ? data.filter((item) => tags.every((tag) => item.tags.includes(tag)))
    //     : data;

    return (
    <div className="search-table-container">
        {filteredData&&filteredData.length > 0 && <div className="search-container">
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
        </div>}
        <div className="table-container">
            {filteredData&&filteredData.length > 0 ?
                <Table data={filteredData} columns={columns}
                       iconStyle={{fontSize: '24px', marginRight: '12px'}}/> : ''}
        </div>
        <ConfirmationModal
            show={showDeleteModal}
            onHide={() => setShowDeleteModal(false)}
            onConfirm={confirmDelete}
            rowData={selectedRowData}
            bodyContent={
                <>
                    <p>Are you sure you want to delete this job application?</p>
                    {selectedRowData && (
                        <div>
                            <p>
                                <strong>Job URL: </strong>
                                <a href={selectedRowData.companyUrl} target="_blank"
                                   rel="noopener noreferrer">
                                    {selectedRowData.company}
                                </a>
                            </p>
                            <p><strong>Role: </strong> {selectedRowData.position}</p>
                            <p><strong>Status : </strong> {selectedRowData.status}</p>
                        </div>
                    )}
                </>
            }
        />
    </div>
    );
};

export default TableContainer;